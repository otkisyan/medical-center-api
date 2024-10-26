package com.medicalcenter.receptionapi.service;

import com.medicalcenter.receptionapi.annotation.CheckDoctorAppointmentOwnership;
import com.medicalcenter.receptionapi.domain.Consultation;
import com.medicalcenter.receptionapi.dto.consultation.ConsultationRequestDto;
import com.medicalcenter.receptionapi.dto.consultation.ConsultationResponseDto;
import com.medicalcenter.receptionapi.exception.ResourceNotFoundException;
import com.medicalcenter.receptionapi.mapper.ConsultationMapper;
import com.medicalcenter.receptionapi.repository.ConsultationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConsultationService {

  private final ConsultationRepository consultationRepository;
  private final ConsultationMapper consultationMapper;

  public ConsultationResponseDto findConsultationById(Long appointmentId) {
    Consultation consultation =
        consultationRepository.findById(appointmentId).orElseThrow(ResourceNotFoundException::new);
    return consultationMapper.consultationToConsultationResponseDto(consultation);
  }

  @CheckDoctorAppointmentOwnership
  public ConsultationResponseDto updateConsultation(
      ConsultationRequestDto consultationRequestDto, Long appointmentId) {
    Consultation consultationToUpdate =
        consultationRepository.findById(appointmentId).orElseThrow(ResourceNotFoundException::new);
    Consultation consultationUpdateRequest =
        consultationMapper.consultationRequestDtoToConsultation(consultationRequestDto);
    BeanUtils.copyProperties(consultationUpdateRequest, consultationToUpdate, "appointment", "id");
    Consultation updatedConsultation = consultationRepository.save(consultationToUpdate);
    return consultationMapper.consultationToConsultationResponseDto(updatedConsultation);
  }
}
