package com.medicalcenter.receptionapi.service;

import com.medicalcenter.receptionapi.domain.Role;
import com.medicalcenter.receptionapi.security.constants.SecurityConstants;
import com.medicalcenter.receptionapi.domain.RefreshSession;
import com.medicalcenter.receptionapi.domain.User;
import com.medicalcenter.receptionapi.dto.user.*;
import com.medicalcenter.receptionapi.exception.InvalidTokenException;
import com.medicalcenter.receptionapi.exception.InvalidTokenTypeException;
import com.medicalcenter.receptionapi.exception.RefreshTokenNotFoundException;
import com.medicalcenter.receptionapi.exception.UserAlreadyExistsException;
import com.medicalcenter.receptionapi.repository.RefreshSessionRepository;
import com.medicalcenter.receptionapi.repository.RoleRepository;
import com.medicalcenter.receptionapi.repository.UserRepository;
import com.medicalcenter.receptionapi.security.CustomUserDetails;
import com.medicalcenter.receptionapi.security.JwtTokenProvider;
import com.medicalcenter.receptionapi.security.enums.JwtType;
import com.medicalcenter.receptionapi.security.enums.RoleAuthority;
import jakarta.servlet.http.HttpServletRequest;
import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RefreshSessionRepository refreshSessionRepository;
    private final UserDetailsService userDetailsService;

    /**
     * Saves a new user to the DB on the provided registration {@link RegisterRequestDto}
     *
     * @param registerRequestDto An object containing user registration credentials (username and password)
     * @throws UserAlreadyExistsException If a user with the given username already exists in the system.
     */
    public void saveUser(RegisterRequestDto registerRequestDto) {
        boolean exists = userRepository.existsByUsername(registerRequestDto.getUsername());
        if (exists) {
            throw new UserAlreadyExistsException("user: " + registerRequestDto.getUsername() + " already exists");
        }
        String passwordHash = encoder.encode(registerRequestDto.getPassword());
        Role role = roleRepository.findByName(RoleAuthority.RECEPTIONIST.authority);
        User user = User.builder()
                .username(registerRequestDto.getUsername())
                .password(passwordHash)
                .roles(Collections.singletonList(role))
                .build();
        userRepository.save(user);
    }

    /**
     * Authenticates a user based on the provided {@link AuthRequestDto} and generates access and refresh tokens upon successful authentication.
     *
     * @param authRequestDto An object containing the user's authentication credentials (username and password).
     * @return A Pair containing a ResponseCookie representing the refresh token cookie and
     * an AuthResponseDto containing the generated access and refresh tokens along with their expirations dates
     * @throws org.springframework.security.core.AuthenticationException               If authentication fails.
     * @throws UsernameNotFoundException If the user is not found.
     */
    public Pair<ResponseCookie, AuthResponseDto> authUser(AuthRequestDto authRequestDto) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequestDto.getUsername(),
                authRequestDto.getPassword()));
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = findUserByUsername(authRequestDto.getUsername());
        TokenDto refreshTokenDto = jwtTokenProvider.generateRefreshToken(userDetails);
        TokenDto accessTokenDto = jwtTokenProvider.generateAccessToken(userDetails);
        refreshSessionRepository.save(RefreshSession.builder()
                .token(refreshTokenDto.getToken())
                .user(user)
                .build());
        ResponseCookie refreshTokenCookie = createRefreshTokenCookie(refreshTokenDto);
        AuthResponseDto authResponseDto = createAuthResponseDto(accessTokenDto, refreshTokenDto);
        return new Pair<>(refreshTokenCookie, authResponseDto);
    }

    /**
     * Refreshes the JWT tokens (access and refresh tokens) for the user based on the provided
     * HttpServletRequest and returns an {@link AuthResponseDto} containing the new tokens and their expiration dates.
     *
     * @param request The HttpServletRequest containing the refresh token to perform token refresh.
     * @return A Pair containing a ResponseCookie representing the refresh token cookie and
     * an AuthResponseDto containing the generated access and refresh tokens along with their expirations dates
     * @throws InvalidTokenException         If the provided refresh token is null or invalid.
     * @throws InvalidTokenTypeException     If the refresh token is of an invalid token type.
     * @throws RefreshTokenNotFoundException If the refresh token is not found in the repository.
     * @throws UsernameNotFoundException     If the associated user is not found in the repository.
     */
    public Pair<ResponseCookie, AuthResponseDto> refresh(HttpServletRequest request) {
        String refreshToken = getRefreshToken(request) ;
        String username = jwtTokenProvider.getUsernameFromJwt(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        TokenDto refreshTokenDto = jwtTokenProvider.generateRefreshToken(userDetails);
        TokenDto accessTokenDto = jwtTokenProvider.generateAccessToken(userDetails);
        RefreshSession refreshSession = findRefreshSessionByToken(refreshToken);
        refreshSession.setToken(refreshTokenDto.getToken());
        refreshSessionRepository.save(refreshSession);
        ResponseCookie refreshTokenCookie = createRefreshTokenCookie(refreshTokenDto);
        AuthResponseDto authResponseDto = createAuthResponseDto(accessTokenDto, refreshTokenDto);
        return new Pair<>(refreshTokenCookie, authResponseDto);
    }

    public void logout(HttpServletRequest request){
        String refreshToken = jwtTokenProvider.getRefreshTokenFromHttpOnlyCookie(request);
        if (refreshToken == null){
            refreshToken = jwtTokenProvider.getJwtFromAuthorizationHeader(request);
        }
        if (refreshToken != null){
            RefreshSession refreshSession = findRefreshSessionByToken(refreshToken);
            refreshSessionRepository.delete(refreshSession);
        }
    }

    public boolean validateRefreshToken(HttpServletRequest request){
        String refreshToken = getRefreshToken(request);
        RefreshSession refreshSession = findRefreshSessionByToken(refreshToken);
        return refreshToken != null && refreshSession != null;
    }

    public boolean validateRefreshToken(String refreshToken){
        RefreshSession refreshSession = findRefreshSessionByToken(refreshToken);
        return refreshToken != null && refreshSession != null;
    }

    public UserDetailsDto getUserDetailsDto(HttpServletRequest request){
       String refreshToken = getRefreshToken(request);
       validateRefreshToken(refreshToken);
       String username = jwtTokenProvider.getUsernameFromJwt(refreshToken);
       CustomUserDetails customUserDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);
       return UserDetailsDto.ofUserDetails(customUserDetails);
    }

    public String getRefreshToken(HttpServletRequest request){
        String refreshToken = jwtTokenProvider.getRefreshTokenFromHttpOnlyCookie(request);
        if(refreshToken == null || !jwtTokenProvider.validateToken(refreshToken)){
            refreshToken = jwtTokenProvider.getJwtFromAuthorizationHeader(request);
            if (refreshToken == null || jwtTokenProvider.validateToken(refreshToken)){
                throw new InvalidTokenException();
            }
        }
        JwtType jwtType = getJwtType(refreshToken);
        if (jwtType != JwtType.REFRESH) {
            throw new InvalidTokenTypeException();
        }
        return refreshToken;
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
        return ResponseCookie.from(SecurityConstants.REFRESH_TOKEN_COOKIE_NAME, refreshToken.getToken())
                .httpOnly(true)
                .secure(true)
                .path(SecurityConstants.REFRESH_TOKEN_COOKIE_PATH)
                .maxAge(expiresInSec)
                .sameSite("None")
                .build();
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User: " + username + " not found"));
    }

    private RefreshSession findRefreshSessionByToken(String refreshToken) {
        return refreshSessionRepository.findByToken(refreshToken).orElseThrow(RefreshTokenNotFoundException::new);
    }

    private JwtType getJwtType(String token) throws InvalidTokenTypeException {
        try {
            return jwtTokenProvider.getJwtType(token);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new InvalidTokenTypeException();
        }
    }
}
