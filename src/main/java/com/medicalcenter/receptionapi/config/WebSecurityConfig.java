package com.medicalcenter.receptionapi.config;

import com.medicalcenter.receptionapi.security.JwtAuthenticationFilter;
import com.medicalcenter.receptionapi.security.enums.RoleAuthority;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {

  @Value("${cors.allowed-origins}")
  private List<String> corsAllowedOrigins;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests(
            (requests) ->
                requests
                    .requestMatchers("/actuator/**")
                    .permitAll()
                    // Patients
                    .requestMatchers("/patients/**")
                    .hasAnyRole(
                        RoleAuthority.ADMIN.toString(),
                        RoleAuthority.RECEPTIONIST.toString(),
                        RoleAuthority.DOCTOR.toString())
                    // Doctors
                    .requestMatchers(HttpMethod.DELETE, "/doctors/{id}")
                    .hasAnyRole(RoleAuthority.ADMIN.toString())
                    .requestMatchers(HttpMethod.POST, "/doctors")
                    .hasAnyRole(RoleAuthority.ADMIN.toString())
                    .requestMatchers(HttpMethod.PUT, "/doctors/{id}")
                    .hasAnyRole(RoleAuthority.ADMIN.toString())
                    .requestMatchers(HttpMethod.GET, "/doctors")
                    .hasAnyRole(
                        RoleAuthority.ADMIN.toString(), RoleAuthority.RECEPTIONIST.toString())
                    .requestMatchers(HttpMethod.GET, "/doctors/count")
                    .hasAnyRole(
                        RoleAuthority.ADMIN.toString(), RoleAuthority.RECEPTIONIST.toString())
                    .requestMatchers(HttpMethod.GET, "/doctors/{id}")
                    .hasAnyRole(
                        RoleAuthority.ADMIN.toString(),
                        RoleAuthority.RECEPTIONIST.toString(),
                        RoleAuthority.DOCTOR.toString())
                    .requestMatchers(HttpMethod.GET, "/doctors/{id}/work-schedules")
                    .hasAnyRole(
                        RoleAuthority.ADMIN.toString(),
                        RoleAuthority.RECEPTIONIST.toString(),
                        RoleAuthority.DOCTOR.toString())
                    // Receptionists
                    .requestMatchers(HttpMethod.DELETE, "/receptionists/{id}")
                    .hasAnyRole(RoleAuthority.ADMIN.toString())
                    .requestMatchers(HttpMethod.POST, "/receptionists")
                    .hasAnyRole(RoleAuthority.ADMIN.toString())
                    .requestMatchers(HttpMethod.PUT, "/receptionists/{id}")
                    .hasAnyRole(RoleAuthority.ADMIN.toString())
                    .requestMatchers(HttpMethod.GET, "/receptionists/count")
                    .hasAnyRole(RoleAuthority.ADMIN.toString())
                    .requestMatchers(HttpMethod.GET, "/receptionists")
                    .hasAnyRole(RoleAuthority.ADMIN.toString())
                    .requestMatchers(HttpMethod.GET, "/receptionists/{id}")
                    .hasAnyRole(
                        RoleAuthority.ADMIN.toString(), RoleAuthority.RECEPTIONIST.toString())
                    // Offices
                    .requestMatchers(HttpMethod.GET, "/offices/**")
                    .hasAnyRole(
                        RoleAuthority.ADMIN.toString(),
                        RoleAuthority.RECEPTIONIST.toString(),
                        RoleAuthority.DOCTOR.toString())
                    .requestMatchers(HttpMethod.POST, "/offices")
                    .hasAnyRole(
                        RoleAuthority.ADMIN.toString())
                    .requestMatchers(HttpMethod.PUT, "/offices/{id}")
                    .hasAnyRole(
                        RoleAuthority.ADMIN.toString())
                    .requestMatchers(HttpMethod.DELETE, "/offices/{id}")
                    .hasAnyRole(
                        RoleAuthority.ADMIN.toString())
                    // Work Schedules
                    .requestMatchers(HttpMethod.PUT, "/work-schedules/**")
                    .hasAnyRole(
                        RoleAuthority.RECEPTIONIST.toString(), RoleAuthority.ADMIN.toString())
                    // Appointments
                    .requestMatchers(HttpMethod.GET, "/appointments")
                    .hasAnyRole(
                        RoleAuthority.ADMIN.toString(),
                        RoleAuthority.RECEPTIONIST.toString(),
                        RoleAuthority.DOCTOR.toString())
                    .requestMatchers(HttpMethod.GET, "/appointments/count")
                    .hasAnyRole(
                        RoleAuthority.ADMIN.toString(),
                        RoleAuthority.RECEPTIONIST.toString(),
                        RoleAuthority.DOCTOR.toString())
                    .requestMatchers(HttpMethod.GET, "/appointments/timetable/**")
                    .hasAnyRole(
                        RoleAuthority.ADMIN.toString(),
                        RoleAuthority.RECEPTIONIST.toString(),
                        RoleAuthority.DOCTOR.toString())
                    .requestMatchers(HttpMethod.GET, "/appointments/{id}")
                    .hasAnyRole(
                        RoleAuthority.ADMIN.toString(),
                        RoleAuthority.RECEPTIONIST.toString(),
                        RoleAuthority.DOCTOR.toString())
                    .requestMatchers(HttpMethod.POST, "/appointments")
                    .hasAnyRole(
                        RoleAuthority.ADMIN.toString(),
                        RoleAuthority.RECEPTIONIST.toString(),
                        RoleAuthority.DOCTOR.toString())
                    .requestMatchers(HttpMethod.PUT, "/appointments/{id}")
                    .hasAnyRole(
                        RoleAuthority.ADMIN.toString(),
                        RoleAuthority.RECEPTIONIST.toString(),
                        RoleAuthority.DOCTOR.toString())
                    .requestMatchers(HttpMethod.DELETE, "/appointments/{id}")
                    .hasAnyRole(
                        RoleAuthority.ADMIN.toString(),
                        RoleAuthority.RECEPTIONIST.toString(),
                        RoleAuthority.DOCTOR.toString())
                    .requestMatchers("/appointments/{id}/consultation")
                    .hasAnyRole(RoleAuthority.ADMIN.toString(), RoleAuthority.DOCTOR.toString())
                    // Consultations
                    .requestMatchers(HttpMethod.PUT, "/consultation/**")
                    .hasAnyRole(RoleAuthority.DOCTOR.toString())
                    .requestMatchers(HttpMethod.GET, "/consultation/**")
                    .hasAnyRole(RoleAuthority.ADMIN.toString(), RoleAuthority.DOCTOR.toString())
                    // User
                    .requestMatchers("/user/password")
                    .authenticated()
                    .requestMatchers("/user/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .sessionManagement(
            (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .cors((cors) -> cors.configurationSource(apiConfigurationSource()))
        .csrf((csrf) -> csrf.disable());
    return http.build();
  }

  CorsConfigurationSource apiConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(corsAllowedOrigins);
    configuration.setAllowedHeaders(
        List.of("Authorization", "Cache-Control", "Content-Type", "Origin"));
    configuration.setAllowedMethods(
        Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS", "PATCH"));
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
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter();
  }
}
