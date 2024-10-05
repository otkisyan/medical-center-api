package com.medicalcenter.receptionapi.dto.workschedule;

import com.medicalcenter.receptionapi.domain.WorkSchedule;
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
public class WorkScheduleRequestDto implements Serializable {
  private Long doctorId;
  private Long dayOfWeekId;
  private LocalTime workTimeStart;
  private LocalTime workTimeEnd;

  public static WorkSchedule toEntity(WorkScheduleRequestDto workScheduleRequestDto) {
    return WorkSchedule.builder()
        .workTimeStart(workScheduleRequestDto.getWorkTimeStart())
        .workTimeEnd(workScheduleRequestDto.getWorkTimeEnd())
        .build();
  }
}
