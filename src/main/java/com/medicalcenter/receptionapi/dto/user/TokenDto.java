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
  private String token;
  private String subject;
  private Long userId;
  private JwtType jwtType;
  private Date issuedAtDate;
  private Date expirationDate;
  private long expiresInMs;
}
