package com.medicalcenter.receptionapi.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security.jwt.refresh-token.cookie")
@Getter
@Setter
public class RefreshTokenCookieProperties {
  private String name;
  private String domain;
  private String sameSite;
  private String path;
  private boolean httpOnly;
  private boolean secure;
}
