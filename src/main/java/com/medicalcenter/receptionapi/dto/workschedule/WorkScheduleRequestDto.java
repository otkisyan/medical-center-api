package com.medicalcenter.receptionapi.dto.workschedule;

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
}
