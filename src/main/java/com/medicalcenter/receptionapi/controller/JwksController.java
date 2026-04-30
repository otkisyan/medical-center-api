package com.medicalcenter.receptionapi.controller;


import com.medicalcenter.receptionapi.dto.jwk.JwkDto;
import com.medicalcenter.receptionapi.dto.jwk.JwksResponse;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/.well-known")
public class JwksController {

  private final PublicKey publicKey;

  public JwksController(PublicKey publicKey) {
    this.publicKey = publicKey;
  }

  @GetMapping("/jwks")
  public ResponseEntity<JwksResponse> getJwks() {

    RSAPublicKey rsa = (RSAPublicKey) publicKey;

    JwkDto jwk =
        new JwkDto(
            "RSA",
            "sig",
            "RS256",
            "key-1",
            base64Url(rsa.getModulus()),
            base64Url(rsa.getPublicExponent()));

    return ResponseEntity.ok(new JwksResponse(List.of(jwk)));
  }

  private String base64Url(BigInteger value) {
    return Base64.getUrlEncoder().withoutPadding().encodeToString(value.toByteArray());
  }
}
