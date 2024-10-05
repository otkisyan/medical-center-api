package com.medicalcenter.receptionapi.dto.error;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Violation implements Serializable {
  private String field;
  private String message;
}
