package com.medicalcenter.receptionapi.dto.consultation;

import com.medicalcenter.receptionapi.domain.Consultation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsultationRequestDto implements Serializable {

    private String diagnosis;
    private String symptoms;
    private String medicalRecommendations;

    public static Consultation toEntity(ConsultationRequestDto consultationRequestDto){
        return Consultation.builder()
                .diagnosis(consultationRequestDto.getDiagnosis())
                .symptoms(consultationRequestDto.getSymptoms())
                .medicalRecommendations(consultationRequestDto.getMedicalRecommendations())
                .build();
    }
}
