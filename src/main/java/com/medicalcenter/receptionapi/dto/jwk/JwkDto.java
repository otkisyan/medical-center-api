package com.medicalcenter.receptionapi.dto.jwk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwkDto {
  String kty;
  String use;
  String alg;
  String kid;
  @JsonProperty("n")
  String modulus;
  @JsonProperty("e")
  String exponent;
}
