package com.medicalcenter.receptionapi.service;

import com.medicalcenter.receptionapi.annotation.CheckDoctorAppointmentOwnership;
import com.medicalcenter.receptionapi.domain.Consultation;
import com.medicalcenter.receptionapi.dto.consultation.ConsultationRequestDto;
import com.medicalcenter.receptionapi.dto.consultation.ConsultationResponseDto;
import com.medicalcenter.receptionapi.exception.ResourceNotFoundException;
import com.medicalcenter.receptionapi.repository.ConsultationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConsultationService {

  private ConsultationRepository consultationRepository;

  public ConsultationResponseDto findConsultationById(Long appointmentId) {
    Consultation consultation =
        consultationRepository.findById(appointmentId).orElseThrow(ResourceNotFoundException::new);
    return ConsultationResponseDto.ofEntity(consultation);
  }

  @CheckDoctorAppointmentOwnership
  public ConsultationResponseDto updateConsultation(
      ConsultationRequestDto consultationRequestDto, Long appointmentId) {
    Consultation consultationToUpdate =
        consultationRepository.findById(appointmentId).orElseThrow(ResourceNotFoundException::new);
    Consultation consultationUpdateRequest =
        ConsultationRequestDto.toEntity(consultationRequestDto);
    BeanUtils.copyProperties(consultationUpdateRequest, consultationToUpdate, "appointment", "id");
    Consultation updatedConsultation = consultationRepository.save(consultationToUpdate);
    return ConsultationResponseDto.ofEntity(updatedConsultation);
  }
}
