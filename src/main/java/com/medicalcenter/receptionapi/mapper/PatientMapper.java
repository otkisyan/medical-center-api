package com.medicalcenter.receptionapi.mapper;

import com.medicalcenter.receptionapi.domain.Patient;
import com.medicalcenter.receptionapi.dto.patient.PatientRequestDto;
import com.medicalcenter.receptionapi.dto.patient.PatientResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PatientMapper {
  Patient patientRequestDtoToPatient(PatientRequestDto patientRequestDto);

  PatientResponseDto patientToPatientResponseDto(Patient patient);
}
