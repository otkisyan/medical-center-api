package com.medicalcenter.receptionapi.mapper;

import com.medicalcenter.receptionapi.domain.Appointment;
import com.medicalcenter.receptionapi.dto.appointment.AppointmentRequestDto;
import com.medicalcenter.receptionapi.dto.appointment.AppointmentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    uses = {PatientMapper.class, DoctorMapper.class})
public interface AppointmentMapper {
  @Mapping(source = "doctor", target = "doctorResponseDto")
  @Mapping(source = "patient", target = "patientResponseDto")
  AppointmentResponseDto appointmentToAppointmentResponseDto(Appointment appointment);

  Appointment appointmentRequestDtoToAppointment(AppointmentRequestDto appointmentRequestDto);
}
