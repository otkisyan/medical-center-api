package com.medicalcenter.receptionapi.config;

import com.medicalcenter.receptionapi.security.JwtAuthenticationFilter;
import com.medicalcenter.receptionapi.security.enums.RoleAuthority;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                //exceptionHandling((exception) -> exception.authenticationEntryPoint(jwtAuthEntryPoint))
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/receptionists/**").hasAnyRole(RoleAuthority.ADMIN.toString())
                        .requestMatchers("/patients/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/doctors").hasAnyRole(RoleAuthority.ADMIN.toString())
                        .requestMatchers(HttpMethod.POST, "/doctors").hasAnyRole(RoleAuthority.ADMIN.toString())
                        .requestMatchers(HttpMethod.PUT, "/doctors").hasAnyRole(RoleAuthority.ADMIN.toString())
                        .requestMatchers(HttpMethod.GET, "/doctors").hasAnyRole(
                                RoleAuthority.ADMIN.toString(),
                                RoleAuthority.RECEPTIONIST.toString())
                        .requestMatchers(HttpMethod.GET, "/doctors/**").hasAnyRole(
                                RoleAuthority.ADMIN.toString(),
                                RoleAuthority.RECEPTIONIST.toString(),
                                RoleAuthority.DOCTOR.toString()
                        )
                        .requestMatchers("/doctors/**").hasAnyRole(RoleAuthority.RECEPTIONIST.toString(), RoleAuthority.ADMIN.toString())
                        .requestMatchers("/offices").authenticated()
                        .requestMatchers("/work-schedules").authenticated()
                        .requestMatchers("/appointments").authenticated()
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors((cors) -> cors
                        .configurationSource(apiConfigurationSource()))
                .csrf((csrf) -> csrf.disable());


        return http.build();
    }

    CorsConfigurationSource apiConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://localhost:3001", "http://localhost:3001"));
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type", "Origin"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS", "PATCH"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
}