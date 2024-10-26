package com.medicalcenter.receptionapi.mapper;

import com.medicalcenter.receptionapi.domain.Receptionist;
import com.medicalcenter.receptionapi.dto.receptionist.ReceptionistRequestDto;
import com.medicalcenter.receptionapi.dto.receptionist.ReceptionistResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReceptionistMapper {
  Receptionist receptionistRequestDtoToReceptionist(ReceptionistRequestDto receptionistRequestDto);

  ReceptionistResponseDto receptionistToReceptionistResponseDto(Receptionist receptionist);
}
