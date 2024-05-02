package com.medicalcenter.receptionapi.controller;

import com.medicalcenter.receptionapi.dto.user.*;
import com.medicalcenter.receptionapi.security.CustomUserDetails;
import com.medicalcenter.receptionapi.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import javafx.util.Pair;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto registerRequestDto) {
        authenticationService.saveUser(registerRequestDto);
        return new ResponseEntity<>(registerRequestDto.getUsername() + " is successfully registered", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto) {
        Pair<ResponseCookie, AuthResponseDto> responsePair = authenticationService.authUser(authRequestDto);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, responsePair.getKey().toString())
                .body(responsePair.getValue());
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDto> refresh(HttpServletRequest request) {
        Pair<ResponseCookie, AuthResponseDto> responsePair = authenticationService.refresh(request);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, responsePair.getKey().toString())
                .body(responsePair.getValue());
    }

    @GetMapping("/details")
    public ResponseEntity<UserDetailsDto> getUserDetails(HttpServletRequest request){
        UserDetailsDto userDetailsDto = authenticationService.getUserDetailsDto(request);
        if(userDetailsDto != null){
            return ResponseEntity.ok(userDetailsDto);
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateAuthentication(HttpServletRequest request){
        if (authenticationService.validateRefreshToken(request)) {
            return ResponseEntity.ok(true);
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
    }
}



