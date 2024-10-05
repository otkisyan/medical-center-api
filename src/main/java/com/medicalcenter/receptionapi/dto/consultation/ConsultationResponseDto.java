package com.medicalcenter.receptionapi.dto.consultation;

import com.medicalcenter.receptionapi.domain.Consultation;
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

  public static ConsultationResponseDto ofEntity(Consultation consultation) {
    return ConsultationResponseDto.builder()
        .id(consultation.getId())
        .diagnosis(consultation.getDiagnosis())
        .symptoms(consultation.getSymptoms())
        .medicalRecommendations(consultation.getMedicalRecommendations())
        .build();
  }
}
