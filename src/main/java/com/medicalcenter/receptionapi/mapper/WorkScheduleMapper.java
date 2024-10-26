package com.medicalcenter.receptionapi.mapper;

import com.medicalcenter.receptionapi.domain.WorkSchedule;
import com.medicalcenter.receptionapi.dto.workschedule.WorkScheduleRequestDto;
import com.medicalcenter.receptionapi.dto.workschedule.WorkScheduleResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    uses = {DayOfWeekMapper.class})
public interface WorkScheduleMapper {
  WorkSchedule workScheduleRequestDtoToWorkSchedule(WorkScheduleRequestDto workScheduleRequestDto);

  @Mapping(source = "dayOfWeek", target = "dayOfWeekResponseDto")
  WorkScheduleResponseDto workScheduleToWorkScheduleResponseDto(WorkSchedule workSchedule);
}
