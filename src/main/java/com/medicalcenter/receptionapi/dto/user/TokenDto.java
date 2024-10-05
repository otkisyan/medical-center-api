package com.medicalcenter.receptionapi.dto.user;

import com.medicalcenter.receptionapi.security.enums.JwtType;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDto implements Serializable {
  String token;
  String subject;
  Long userId;
  JwtType jwtType;
  Date issuedAtDate;
  Date expirationDate;
  long expiresInMs;
}
