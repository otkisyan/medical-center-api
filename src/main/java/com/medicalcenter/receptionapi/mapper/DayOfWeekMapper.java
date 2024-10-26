package com.medicalcenter.receptionapi.mapper;

import com.medicalcenter.receptionapi.domain.DayOfWeek;
import com.medicalcenter.receptionapi.dto.dayofweek.DayOfWeekResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DayOfWeekMapper {
  DayOfWeekResponseDto dayOfWeekToDayOfWeekResponseDto(DayOfWeek dayOfWeek);

  DayOfWeek dayOfWeekResponseDtoToDayOfWeek(DayOfWeekResponseDto responseDto);
}
