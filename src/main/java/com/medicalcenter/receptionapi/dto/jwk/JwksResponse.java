package com.medicalcenter.receptionapi.dto.jwk;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwksResponse {
  List<JwkDto> keys;
}
