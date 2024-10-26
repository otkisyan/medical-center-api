package com.medicalcenter.receptionapi.dto.appointment;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentRequestDto implements Serializable {

  @NotNull private LocalDate date;
  @NotNull private LocalTime timeStart;
  @NotNull private LocalTime timeEnd;
  @NotNull private Long patientId;
  @NotNull private Long doctorId;
}
