package com.medicalcenter.receptionapi.service;

import com.medicalcenter.receptionapi.domain.*;
import com.medicalcenter.receptionapi.dto.user.*;
import com.medicalcenter.receptionapi.exception.*;
import com.medicalcenter.receptionapi.mapper.UserMapper;
import com.medicalcenter.receptionapi.repository.RefreshSessionRepository;
import com.medicalcenter.receptionapi.repository.RoleRepository;
import com.medicalcenter.receptionapi.repository.UserRepository;
import com.medicalcenter.receptionapi.security.CustomUserDetails;
import com.medicalcenter.receptionapi.security.JwtTokenProvider;
import com.medicalcenter.receptionapi.security.RefreshTokenCookieProperties;
import com.medicalcenter.receptionapi.security.enums.JwtType;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final BCryptPasswordEncoder encoder;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final UserDetailsService userDetailsService;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final RefreshSessionRepository refreshSessionRepository;
  private final UserMapper userMapper;
  private final RefreshTokenCookieProperties refreshTokenCookieProperties;

  public UserService(
      BCryptPasswordEncoder encoder,
      AuthenticationManager authenticationManager,
      JwtTokenProvider jwtTokenProvider,
      UserDetailsService userDetailsService,
      UserRepository userRepository,
      RoleRepository roleRepository,
      RefreshSessionRepository refreshSessionRepository,
      UserMapper userMapper,
      RefreshTokenCookieProperties refreshTokenCookieProperties) {
    this.encoder = encoder;
    this.authenticationManager = authenticationManager;
    this.jwtTokenProvider = jwtTokenProvider;
    this.userDetailsService = userDetailsService;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.refreshSessionRepository = refreshSessionRepository;
    this.userMapper = userMapper;
    this.refreshTokenCookieProperties = refreshTokenCookieProperties;
  }

  /**
   * Saves a new user to the DB on the provided registration {@link RegisterRequestDto}
   *
   * @param registerRequestDto An object containing user registration credentials (username and
   *     password)
   * @throws UserAlreadyExistsException If a user with the given username already exists in the
   *     system.
   */
  public UserDetailsDto saveUser(RegisterRequestDto registerRequestDto) {
    User newUser = createUser(registerRequestDto);
    return userMapper.userToUserDetailsDto(newUser);
  }

  public UserDetailsDto saveUser(RegisterRequestDto registerRequestDto, Doctor doctor) {
    User newUser = createUser(registerRequestDto);
    if (doctor != null) {
      assignUser(doctor, newUser);
    }
    return userMapper.userToUserDetailsDto(newUser);
  }

  public UserDetailsDto saveUser(RegisterRequestDto registerRequestDto, Receptionist receptionist) {
    User newUser = createUser(registerRequestDto);
    if (receptionist != null) {
      assignUser(receptionist, newUser);
    }
    return userMapper.userToUserDetailsDto(newUser);
  }

  /**
   * Authenticates a user based on the provided {@link UserCredentialsDto} and generates access and
   * refresh tokens upon successful authentication.
   *
   * @param userCredentialsDto An object containing the user's authentication credentials (username
   *     and password).
   * @return An AuthResponseWithCookieDto containing a ResponseCookie representing the refresh token
   *     cookie and an AuthResponseDto containing the generated access and refresh tokens along with
   *     their expirations dates
   * @throws org.springframework.security.core.AuthenticationException If authentication fails.
   * @throws UsernameNotFoundException If the user is not found.
   */
  public AuthResponeWithResponseCookieDto authUser(UserCredentialsDto userCredentialsDto) {
    Authentication auth =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                userCredentialsDto.getUsername(), userCredentialsDto.getPassword()));
    UserDetails userDetails = (UserDetails) auth.getPrincipal();
    User user = findUserByUsername(userCredentialsDto.getUsername());
    TokenDto refreshTokenDto = jwtTokenProvider.generateRefreshToken(userDetails);
    TokenDto accessTokenDto = jwtTokenProvider.generateAccessToken(userDetails);
    refreshSessionRepository.save(
        RefreshSession.builder().token(refreshTokenDto.getToken()).user(user).build());
    ResponseCookie refreshTokenCookie = createRefreshTokenCookie(refreshTokenDto);
    AuthResponseDto authResponseDto = createAuthResponseDto(accessTokenDto, refreshTokenDto);
    return new AuthResponeWithResponseCookieDto(authResponseDto, refreshTokenCookie);
  }

  /**
   * Refreshes the JWT tokens (access and refresh tokens) for the user based on the provided
   * HttpServletRequest and returns an {@link AuthResponseDto} containing the new tokens and their
   * expiration dates.
   *
   * @param request The HttpServletRequest containing the refresh token to perform token refresh.
   * @return An AuthResponseWithCookieDto containing a ResponseCookie representing the refresh token
   *     cookie and an AuthResponseDto containing the generated access and refresh tokens along with
   *     their expirations dates
   * @throws InvalidTokenException If the provided refresh token is null or invalid.
   * @throws InvalidTokenTypeException If the refresh token is of an invalid token type.
   * @throws RefreshTokenNotFoundException If the refresh token is not found in the repository.
   * @throws UsernameNotFoundException If the associated user is not found in the repository.
   */
  public AuthResponeWithResponseCookieDto refresh(HttpServletRequest request) {
    String refreshToken = getRefreshToken(request);
    String username = jwtTokenProvider.getUsernameFromJwt(refreshToken);
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    TokenDto refreshTokenDto = jwtTokenProvider.generateRefreshToken(userDetails);
    TokenDto accessTokenDto = jwtTokenProvider.generateAccessToken(userDetails);
    RefreshSession refreshSession = findRefreshSessionByToken(refreshToken);
    refreshSession.setToken(refreshTokenDto.getToken());
    refreshSessionRepository.save(refreshSession);
    ResponseCookie refreshTokenCookie = createRefreshTokenCookie(refreshTokenDto);
    AuthResponseDto authResponseDto = createAuthResponseDto(accessTokenDto, refreshTokenDto);
    return new AuthResponeWithResponseCookieDto(authResponseDto, refreshTokenCookie);
  }

  public ResponseCookie logout(HttpServletRequest request) {
    String refreshToken = jwtTokenProvider.getRefreshTokenFromHttpOnlyCookie(request);
    if (refreshToken == null) {
      refreshToken = jwtTokenProvider.getJwtFromAuthorizationHeader(request);
    }
    if (refreshToken != null) {
      RefreshSession refreshSession = findRefreshSessionByToken(refreshToken);
      refreshSessionRepository.delete(refreshSession);
    }
    return createEmptyRefreshTokenCookie();
  }

  public void changePassword(ChangePasswordRequestDto changePasswordRequestDto) {
    CustomUserDetails customUserDetails = getCustomUserDetails();
    User user =
        userRepository
            .findById(customUserDetails.getId())
            .orElseThrow(ResourceNotFoundException::new);
    if (!encoder.matches(changePasswordRequestDto.getCurrentPassword(), user.getPassword())) {
      throw new UserPasswordChangeException(
          "The password provided does not match the real password");
    }
    if (!Objects.equals(
        changePasswordRequestDto.getConfirmPassword(), changePasswordRequestDto.getNewPassword())) {
      throw new UserPasswordChangeException(
          "The confirmation password does not match the new password");
    }
    String encryptedNewPassword = encoder.encode(changePasswordRequestDto.getNewPassword());
    user.setPassword(encryptedNewPassword);
    userRepository.save(user);
  }

  public boolean validateRefreshToken(HttpServletRequest request) {
    String refreshToken = getRefreshToken(request);
    RefreshSession refreshSession = findRefreshSessionByToken(refreshToken);
    return refreshToken != null && refreshSession != null;
  }

  public boolean validateRefreshToken(String refreshToken) {
    RefreshSession refreshSession = findRefreshSessionByToken(refreshToken);
    return refreshToken != null && refreshSession != null;
  }

  public UserDetailsDto getUserDetailsDto(HttpServletRequest request) {
    String refreshToken = getRefreshToken(request);
    validateRefreshToken(refreshToken);
    String username = jwtTokenProvider.getUsernameFromJwt(refreshToken);
    CustomUserDetails customUserDetails =
        (CustomUserDetails) userDetailsService.loadUserByUsername(username);
    return userMapper.userDetailsToUserDetailsDto(customUserDetails);
  }

  public CustomUserDetails getCustomUserDetails() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return (CustomUserDetails) authentication.getPrincipal();
  }

  public String getRefreshToken(HttpServletRequest request) {
    String refreshToken = jwtTokenProvider.getRefreshTokenFromHttpOnlyCookie(request);
    if (refreshToken == null || !jwtTokenProvider.validateToken(refreshToken)) {
      refreshToken = jwtTokenProvider.getJwtFromAuthorizationHeader(request);
      if (refreshToken == null || !jwtTokenProvider.validateToken(refreshToken)) {
        throw new InvalidTokenException();
      }
    }
    JwtType jwtType = getJwtType(refreshToken);
    if (jwtType != JwtType.REFRESH) {
      throw new InvalidTokenTypeException();
    }
    return refreshToken;
  }

  public Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  public boolean hasAnyAuthority(String... authorities) {
    Authentication authentication = getAuthentication();
    if (authentication != null) {
      for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
        for (String authority : authorities) {
          if (authority.equals(grantedAuthority.getAuthority())) {
            return true;
          }
        }
      }
    }
    return false;
  }

  private AuthResponseDto createAuthResponseDto(TokenDto accessToken, TokenDto refreshToken) {
    return AuthResponseDto.builder()
        .accessToken(accessToken.getToken())
        .refreshToken(refreshToken.getToken())
        .accessTokenExpiration(accessToken.getExpirationDate())
        .refreshTokenExpiration(refreshToken.getExpirationDate())
        .build();
  }

  private ResponseCookie createRefreshTokenCookie(TokenDto refreshToken) {
    long expiresInSec = refreshToken.getExpiresInMs() / 1000;
    ResponseCookie.ResponseCookieBuilder builder =
        ResponseCookie.from(refreshTokenCookieProperties.getName(), refreshToken.getToken())
            .httpOnly(refreshTokenCookieProperties.isHttpOnly())
            .secure(refreshTokenCookieProperties.isSecure())
            .path(refreshTokenCookieProperties.getPath())
            .maxAge(expiresInSec)
            .sameSite(refreshTokenCookieProperties.getSameSite());
    setRefreshTokenCookieDomain(builder);
    return builder.build();
  }

  private ResponseCookie createEmptyRefreshTokenCookie() {
    ResponseCookie.ResponseCookieBuilder builder =
        ResponseCookie.from(refreshTokenCookieProperties.getName(), "")
            .httpOnly(refreshTokenCookieProperties.isHttpOnly())
            .secure(refreshTokenCookieProperties.isSecure())
            .path(refreshTokenCookieProperties.getPath())
            .maxAge(0)
            .sameSite(refreshTokenCookieProperties.getSameSite());
    setRefreshTokenCookieDomain(builder);
    return builder.build();
  }

  private void setRefreshTokenCookieDomain(ResponseCookie.ResponseCookieBuilder builder) {
    String domain = refreshTokenCookieProperties.getDomain();
    if (domain != null && !domain.equalsIgnoreCase("localhost")) {
      builder.domain(domain);
    }
  }

  private User findUserByUsername(String username) {
    return userRepository
        .findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User: " + username + " not found"));
  }

  private RefreshSession findRefreshSessionByToken(String refreshToken) {
    return refreshSessionRepository
        .findByToken(refreshToken)
        .orElseThrow(RefreshTokenNotFoundException::new);
  }

  private JwtType getJwtType(String token) throws InvalidTokenTypeException {
    try {
      return jwtTokenProvider.getJwtType(token);
    } catch (IllegalArgumentException | NullPointerException ex) {
      throw new InvalidTokenTypeException();
    }
  }

  private User createUser(RegisterRequestDto registerRequestDto) {
    boolean exists =
        userRepository.existsByUsername(registerRequestDto.getUserCredentialsDto().getUsername());
    if (exists) {
      throw new UserAlreadyExistsException(
          "user: " + registerRequestDto.getUserCredentialsDto().getUsername() + " already exists");
    }
    String passwordHash = encoder.encode(registerRequestDto.getUserCredentialsDto().getPassword());
    Role role = roleRepository.findByName(registerRequestDto.getRole().authority);
    User user =
        User.builder()
            .username(registerRequestDto.getUserCredentialsDto().getUsername())
            .password(passwordHash)
            .roles(Collections.singletonList(role))
            .accountNonExpired(true)
            .accountNonLocked(true)
            .credentialsNonExpired(true)
            .enabled(true)
            .build();
    return userRepository.save(user);
  }

  private void assignUser(Doctor doctor, User user) {
    doctor.setUser(user);
  }

  private void assignUser(Receptionist receptionist, User user) {
    receptionist.setUser(user);
  }

  private String generateRandomString(int length) {
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    Random random = new Random();
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      sb.append(characters.charAt(random.nextInt(characters.length())));
    }
    return sb.toString();
  }

  public boolean userExistsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  public UserCredentialsDto generateRandomCredentials() {
    String username = generateRandomString(8);
    String password = generateRandomString(12);
    UserCredentialsDto credentials =
        UserCredentialsDto.builder().username(username).password(password).build();
    if (userExistsByUsername(username)) {
      return generateRandomCredentials();
    }
    return credentials;
  }
}
