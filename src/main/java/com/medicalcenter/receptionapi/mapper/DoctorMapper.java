package com.medicalcenter.receptionapi.mapper;

import com.medicalcenter.receptionapi.domain.Doctor;
import com.medicalcenter.receptionapi.dto.doctor.DoctorRequestDto;
import com.medicalcenter.receptionapi.dto.doctor.DoctorResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    uses = {OfficeMapper.class})
public interface DoctorMapper {
  Doctor doctorRequestDtoToDoctor(DoctorRequestDto doctorRequestDto);

  DoctorResponseDto doctorToDoctorResponseDto(Doctor doctor);
}
