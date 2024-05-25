package com.medicalcenter.receptionapi.controller;

import com.medicalcenter.receptionapi.dto.user.*;
import com.medicalcenter.receptionapi.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import javafx.util.Pair;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto registerRequestDto) {
        userService.saveUser(registerRequestDto);
        return new ResponseEntity<>(registerRequestDto.getUserCredentialsDto().getUsername() + " is successfully registered", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid UserCredentialsDto userCredentialsDto) {
        Pair<ResponseCookie, AuthResponseDto> responsePair = userService.authUser(userCredentialsDto);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, responsePair.getKey().toString())
                .body(responsePair.getValue());
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDto> refresh(HttpServletRequest request) {
        Pair<ResponseCookie, AuthResponseDto> responsePair = userService.refresh(request);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, responsePair.getKey().toString())
                .body(responsePair.getValue());
    }

    @GetMapping("/details")
    public ResponseEntity<UserDetailsDto> getUserDetails(HttpServletRequest request) {
        UserDetailsDto userDetailsDto = userService.getUserDetailsDto(request);
        if (userDetailsDto != null) {
            return ResponseEntity.ok(userDetailsDto);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateAuthentication(HttpServletRequest request) {
        if (userService.validateRefreshToken(request)) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        ResponseCookie emptyRefreshTokenCookie = userService.logout(request);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, emptyRefreshTokenCookie.toString())
                .body("Logout successful");
    }

    @PutMapping("/password")
    public ResponseEntity<String> changePassword(@RequestBody @Valid ChangePasswordRequestDto changePasswordRequestDto) {
        userService.changePassword(changePasswordRequestDto);
        return ResponseEntity
                .ok("Password has been successfully changed");
    }
}



