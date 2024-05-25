package com.medicalcenter.receptionapi.dto.appointment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.medicalcenter.receptionapi.domain.Appointment;
import com.medicalcenter.receptionapi.dto.doctor.DoctorResponseDto;
import com.medicalcenter.receptionapi.dto.patient.PatientResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDto implements Serializable {
    private Long id;
    private LocalDate date;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    @JsonProperty("doctor")
    private DoctorResponseDto doctorResponseDto;
    @JsonProperty("patient")
    private PatientResponseDto patientResponseDto;

    public static AppointmentResponseDto ofEntity(Appointment appointment) {
        return AppointmentResponseDto.builder()
                .id(appointment.getId())
                .date(appointment.getDate())
                .timeStart(appointment.getTimeStart())
                .timeEnd(appointment.getTimeEnd())
                .patientResponseDto(PatientResponseDto.ofEntity(appointment.getPatient()))
                .doctorResponseDto(DoctorResponseDto.ofEntity(appointment.getDoctor()))
                .build();
    }
}
