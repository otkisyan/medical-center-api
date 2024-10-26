package com.medicalcenter.receptionapi.mapper;

import com.medicalcenter.receptionapi.domain.Consultation;
import com.medicalcenter.receptionapi.dto.consultation.ConsultationRequestDto;
import com.medicalcenter.receptionapi.dto.consultation.ConsultationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ConsultationMapper {
  Consultation consultationRequestDtoToConsultation(ConsultationRequestDto consultationRequestDto);

  ConsultationResponseDto consultationToConsultationResponseDto(Consultation consultation);
}
