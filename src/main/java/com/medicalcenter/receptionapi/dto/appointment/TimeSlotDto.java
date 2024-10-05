package com.medicalcenter.receptionapi.dto.appointment;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeSlotDto implements Serializable {
  List<AppointmentResponseDto> appointments;
  private LocalTime startTime;
  private LocalTime endTime;
}
