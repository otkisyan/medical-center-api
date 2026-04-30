package com.medicalcenter.receptionapi.security;

import com.medicalcenter.receptionapi.dto.user.TokenDto;
import com.medicalcenter.receptionapi.security.constants.SecurityConstants;
import com.medicalcenter.receptionapi.security.enums.JwtType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  private final String jwtSecret;
  private final long jwtAccessTokenExpiration;
  private final long jwtRefreshTokenExpiration;
  private final PrivateKey privateKey;
  private final PublicKey publicKey;

  public JwtTokenProvider(
      @Value("${security.jwt.secret}") String jwtSecret,
      @Value("${security.jwt.access-token.expiration}") long jwtAccessTokenExpiration,
      @Value("${security.jwt.refresh-token.expiration}") long jwtRefreshTokenExpiration,
      PrivateKey privateKey,
      PublicKey publicKey) {
    this.jwtSecret = jwtSecret;
    this.jwtAccessTokenExpiration = jwtAccessTokenExpiration;
    this.jwtRefreshTokenExpiration = jwtRefreshTokenExpiration;
    this.privateKey = privateKey;
    this.publicKey = publicKey;
  }

  public TokenDto generateAccessToken(UserDetails userDetails) {
    return buildToken(userDetails, jwtAccessTokenExpiration, JwtType.ACCESS);
  }

  public TokenDto generateRefreshToken(UserDetails userDetails) {
    return buildToken(userDetails, jwtRefreshTokenExpiration, JwtType.REFRESH);
  }

  public TokenDto buildToken(UserDetails userDetails, long tokenExpiration, JwtType jwtType) {
    CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
    List<String> roles =
        customUserDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    Date currentDate = new Date();
    Date expirationDate = new Date(currentDate.getTime() + tokenExpiration);
    String token =
        Jwts.builder()
            .setSubject(customUserDetails.getUsername())
            .claim("userId", customUserDetails.getId())
            .claim("type", jwtType)
            .claim("roles", roles)
            .setIssuedAt(currentDate)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.RS256, privateKey)
            .compact();
    return TokenDto.builder()
        .token(token)
        .subject(customUserDetails.getUsername())
        .userId(customUserDetails.getId())
        .jwtType(jwtType)
        .issuedAtDate(currentDate)
        .expirationDate(expirationDate)
        .expiresInMs(tokenExpiration)
        .build();
  }

  public String getUsernameFromJwt(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date getExpireDateFromJwt(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public String getJwtFromAuthorizationHeader(HttpServletRequest request) {
    String authHeader = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);
    if (authHeader != null
        && authHeader.startsWith(SecurityConstants.AUTHORIZATION_HEADER_PREFIX)) {
      return authHeader.substring(SecurityConstants.AUTHORIZATION_HEADER_PREFIX.length());
    }
    return null;
  }

  public String getRefreshTokenFromHttpOnlyCookie(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(SecurityConstants.REFRESH_TOKEN_COOKIE_NAME)) {
          return cookie.getValue();
        }
      }
    }
    return null;
  }

  public JwtType getJwtType(String token) {
    String jwtType = extractClaim(token, claims -> claims.get("type", String.class));
    return JwtType.valueOf(jwtType);
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }
}
