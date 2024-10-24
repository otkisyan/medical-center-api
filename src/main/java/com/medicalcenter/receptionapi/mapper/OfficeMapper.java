package com.medicalcenter.receptionapi.mapper;

import com.medicalcenter.receptionapi.domain.Office;
import com.medicalcenter.receptionapi.dto.office.OfficeRequestDto;
import com.medicalcenter.receptionapi.dto.office.OfficeResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OfficeMapper {
  Office officeRequestDtoToOffice(OfficeRequestDto officeRequestDto);

  OfficeResponseDto officeToOfficeResponseDto(Office office);
}
