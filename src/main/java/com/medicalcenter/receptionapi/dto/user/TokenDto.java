package com.medicalcenter.receptionapi.dto.user;

import com.medicalcenter.receptionapi.security.enums.JwtType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDto {
    String token;
    String subject;
    Long userId;
    JwtType jwtType;
    Date issuedAtDate;
    Date expirationDate;
    long expiresInMs;
}
