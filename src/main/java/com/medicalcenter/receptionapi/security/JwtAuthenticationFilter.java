package com.medicalcenter.receptionapi.security;

import com.medicalcenter.receptionapi.exception.InvalidTokenException;
import com.medicalcenter.receptionapi.exception.InvalidTokenTypeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    private final List<String> publicUrls = List.of("/auth");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            if (isPublicPath(request)) {
                filterChain.doFilter(request, response);
                return;
            }
            String token = jwtTokenProvider.getJwtFromAuthorizationHeader(request);
            validateTokenAndAuthenticateUser(token);
            filterChain.doFilter(request, response);
        } catch (InvalidTokenTypeException | InvalidTokenException ex) {
            handleAuthenticationFailure(response, ex);
        }
    }

    private boolean isPublicPath(HttpServletRequest request) {
        String path = request.getServletPath();
        for (String publicUrl : publicUrls) {
            if (path.startsWith(publicUrl)) {
                return true;
            }
        }
        return false;
    }

    private void validateTokenAndAuthenticateUser(String token)
            throws InvalidTokenTypeException, InvalidTokenException {
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            throw new InvalidTokenException();
        }
        JwtType jwtType = getJwtType(token);
        if (jwtType == JwtType.REFRESH) {
            throw new InvalidTokenTypeException();
        }
        String username = jwtTokenProvider.getUsernameFromJwt(token);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        authenticateUser(userDetails);
    }

    private void authenticateUser(UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private JwtType getJwtType(String token) throws InvalidTokenTypeException {
        try {
            return jwtTokenProvider.getJwtType(token);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new InvalidTokenTypeException();
        }
    }

    private void handleAuthenticationFailure(HttpServletResponse response, RuntimeException ex) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(ex.getMessage());
    }
}
