package com.medicalcenter.receptionapi.dto.workschedule;

import com.medicalcenter.receptionapi.domain.WorkSchedule;
import com.medicalcenter.receptionapi.dto.dayofweek.DayOfWeekResponseDto;
import java.io.Serializable;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkScheduleResponseDto implements Serializable {

  private Long id;
  private DayOfWeekResponseDto dayOfWeekResponseDto;
  private LocalTime workTimeStart;
  private LocalTime workTimeEnd;

  public static WorkScheduleResponseDto ofEntity(WorkSchedule workSchedule) {
    return WorkScheduleResponseDto.builder()
        .id(workSchedule.getId())
        .dayOfWeekResponseDto(DayOfWeekResponseDto.ofEntity(workSchedule.getDayOfWeek()))
        .workTimeStart(workSchedule.getWorkTimeStart())
        .workTimeEnd(workSchedule.getWorkTimeEnd())
        .build();
  }
}
