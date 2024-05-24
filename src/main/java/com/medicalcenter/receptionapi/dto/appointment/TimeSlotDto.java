package com.medicalcenter.receptionapi.dto.appointment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeSlotDto implements Serializable {
    private LocalTime startTime;
    private LocalTime endTime;
    List<AppointmentResponseDto> appointments;
}
