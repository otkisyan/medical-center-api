package com.medicalcenter.receptionapi.dto.user;

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
public class AuthResponseDto implements Serializable {

  private final String tokenType = "Bearer ";
  private String accessToken;
  private String refreshToken;
  private Date accessTokenExpiration;
  private Date refreshTokenExpiration;
}
