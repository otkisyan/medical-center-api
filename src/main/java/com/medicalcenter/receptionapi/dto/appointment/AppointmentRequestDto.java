package com.medicalcenter.receptionapi.dto.appointment;

import com.medicalcenter.receptionapi.domain.Appointment;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentRequestDto implements Serializable {

    @NotNull
    private LocalDate date;
    @NotNull
    private LocalTime timeStart;
    @NotNull
    private LocalTime timeEnd;
    @NotNull
    private Long patientId;
    @NotNull
    private Long doctorId;

    public static Appointment toEntity(AppointmentRequestDto appointmentRequestDto) {
        return Appointment.builder()
                .date(appointmentRequestDto.getDate())
                .timeStart(appointmentRequestDto.getTimeStart())
                .timeEnd(appointmentRequestDto.getTimeEnd())
                .build();
    }
}
