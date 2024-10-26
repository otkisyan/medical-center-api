package com.medicalcenter.receptionapi.dto.consultation;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsultationResponseDto implements Serializable {

  private Long id;
  private String diagnosis;
  private String symptoms;
  private String medicalRecommendations;
}
