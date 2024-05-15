package com.medicalcenter.receptionapi.dto.appointment;

import com.medicalcenter.receptionapi.domain.Appointment;
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

    private LocalDate date;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private String diagnosis;
    private String symptoms;
    private String medicalRecommendations;
    private Long patientId;
    private Long doctorId;

    public static Appointment toEntity(AppointmentRequestDto appointmentRequestDto) {
        return Appointment.builder()
                .date(appointmentRequestDto.getDate())
                .diagnosis(appointmentRequestDto.getDiagnosis())
                .symptoms(appointmentRequestDto.getSymptoms())
                .medicalRecommendations(appointmentRequestDto.getMedicalRecommendations())
                .timeStart(appointmentRequestDto.getTimeStart())
                .timeEnd(appointmentRequestDto.getTimeEnd())
                .build();
    }
}
