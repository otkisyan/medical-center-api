package com.medicalcenter.receptionapi.aspect;

import com.medicalcenter.receptionapi.domain.Appointment;
import com.medicalcenter.receptionapi.dto.appointment.AppointmentRequestDto;
import com.medicalcenter.receptionapi.dto.consultation.ConsultationRequestDto;
import com.medicalcenter.receptionapi.exception.ResourceNotFoundException;
import com.medicalcenter.receptionapi.repository.AppointmentRepository;
import com.medicalcenter.receptionapi.security.CustomUserDetails;
import com.medicalcenter.receptionapi.security.enums.RoleAuthority;
import com.medicalcenter.receptionapi.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AppointmentOwnershipAspect {

  private final UserService userService;
  private final AppointmentRepository appointmentRepository;

  @Autowired
  public AppointmentOwnershipAspect(
      UserService userService, AppointmentRepository appointmentRepository) {
    this.userService = userService;
    this.appointmentRepository = appointmentRepository;
  }

  @Around(
      value =
          "@annotation(com.medicalcenter.receptionapi.annotation.CheckDoctorAppointmentOwnership)"
              + " && args(appointmentRequestDto, appointmentId)",
      argNames = "joinPoint,appointmentRequestDto, appointmentId")
  public Object checkOwnership(
      ProceedingJoinPoint joinPoint,
      AppointmentRequestDto appointmentRequestDto,
      Long appointmentId)
      throws Throwable {
    CustomUserDetails customUserDetails = userService.getCustomUserDetails();
    Appointment appointmentToUpdate =
        appointmentRepository.findById(appointmentId).orElseThrow(ResourceNotFoundException::new);
    if (userService.hasAnyAuthority(RoleAuthority.DOCTOR.authority)
        && !customUserDetails.getId().equals(appointmentToUpdate.getDoctor().getId())) {
      throw new AccessDeniedException(
          "The doctor can edit only those appointments that are assigned to him");
    }
    return joinPoint.proceed();
  }

  @Around(
      value =
          "@annotation(com.medicalcenter.receptionapi.annotation.CheckDoctorAppointmentOwnership)"
              + " && args(consultationRequestDto, appointmentId)",
      argNames = "joinPoint,consultationRequestDto, appointmentId")
  public Object checkOwnership(
      ProceedingJoinPoint joinPoint,
      ConsultationRequestDto consultationRequestDto,
      Long appointmentId)
      throws Throwable {
    CustomUserDetails customUserDetails = userService.getCustomUserDetails();
    Appointment appointmentToUpdate =
        appointmentRepository.findById(appointmentId).orElseThrow(ResourceNotFoundException::new);
    if (userService.hasAnyAuthority(RoleAuthority.DOCTOR.authority)
        && !customUserDetails.getId().equals(appointmentToUpdate.getDoctor().getId())) {
      throw new AccessDeniedException(
          "The doctor can edit only those appointments that are assigned to him");
    }
    return joinPoint.proceed();
  }

  @Around(
      value =
          "@annotation(com.medicalcenter.receptionapi.annotation.CheckDoctorAppointmentOwnership)"
              + " && args(appointmentId)",
      argNames = "joinPoint, appointmentId")
  public Object checkOwnership(ProceedingJoinPoint joinPoint, Long appointmentId) throws Throwable {
    CustomUserDetails customUserDetails = userService.getCustomUserDetails();
    Appointment appointmentToUpdate =
        appointmentRepository.findById(appointmentId).orElseThrow(ResourceNotFoundException::new);
    if (userService.hasAnyAuthority(RoleAuthority.DOCTOR.authority)
        && !customUserDetails.getId().equals(appointmentToUpdate.getDoctor().getId())) {
      throw new AccessDeniedException(
          "The doctor can delete only those appointments that are assigned to him");
    }
    return joinPoint.proceed();
  }
}
