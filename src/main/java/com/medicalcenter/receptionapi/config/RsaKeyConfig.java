package com.medicalcenter.receptionapi.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RsaKeyConfig {

  private final KeyPair keyPair;

  public RsaKeyConfig() {
    try {
      KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
      generator.initialize(2048);
      this.keyPair = generator.generateKeyPair();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Bean
  public PrivateKey privateKey() {
    return keyPair.getPrivate();
  }

  @Bean
  public PublicKey publicKey() {
    return keyPair.getPublic();
  }
}
