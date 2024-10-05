package com.medicalcenter.receptionapi.service;

import com.medicalcenter.receptionapi.domain.Consultation;
import com.medicalcenter.receptionapi.dto.consultation.ConsultationRequestDto;
import com.medicalcenter.receptionapi.dto.consultation.ConsultationResponseDto;
import com.medicalcenter.receptionapi.exception.ResourceNotFoundException;
import com.medicalcenter.receptionapi.repository.ConsultationRepository;
import com.medicalcenter.receptionapi.security.CustomUserDetails;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConsultationService {

  private ConsultationRepository consultationRepository;
  private UserService userService;

  public ConsultationResponseDto findConsultationById(Long appointmentId) {
    Consultation consultation =
        consultationRepository.findById(appointmentId).orElseThrow(ResourceNotFoundException::new);
    return ConsultationResponseDto.ofEntity(consultation);
  }

  public ConsultationResponseDto updateConsultation(
      Long appointmentId, ConsultationRequestDto consultationRequestDto) {
    CustomUserDetails customUserDetails = userService.getCustomUserDetails();
    Consultation consultationToUpdate =
        consultationRepository.findById(appointmentId).orElseThrow(ResourceNotFoundException::new);
    if (!Objects.equals(
        consultationToUpdate.getAppointment().getDoctor().getId(), customUserDetails.getId())) {
      throw new AccessDeniedException(
          "Only the doctor to whom the appointment belongs can change the consultation data.");
    }
    Consultation consultationUpdateRequest =
        ConsultationRequestDto.toEntity(consultationRequestDto);
    BeanUtils.copyProperties(consultationUpdateRequest, consultationToUpdate, "appointment", "id");
    Consultation updatedConsultation = consultationRepository.save(consultationToUpdate);
    return ConsultationResponseDto.ofEntity(updatedConsultation);
  }
}
