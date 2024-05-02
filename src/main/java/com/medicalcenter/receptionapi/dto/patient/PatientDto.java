package com.medicalcenter.receptionapi.dto.patient;

import com.medicalcenter.receptionapi.domain.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {
    private Long id;
    private String name;
    private String surname;
    private String middleName;
    private String address;
    private String phone;
    private String messengerContact;
    private LocalDate birthDate;
    private String preferentialCategory;

    public static PatientDto ofEntity(Patient patient) {
        return PatientDto.builder()
                .id(patient.getId())
                .name(patient.getName())
                .surname(patient.getSurname())
                .middleName(patient.getMiddleName())
                .address(patient.getAddress())
                .phone(patient.getAddress())
                .messengerContact(patient.getMessengerContact())
                .birthDate(patient.getBirthDate())
                .preferentialCategory(patient.getPreferentialCategory())
                .build();
    }

    public static Patient toEntity(PatientDto patientDto) {
        return Patient.builder()
                .name(patientDto.getName())
                .surname(patientDto.getSurname())
                .middleName(patientDto.getMiddleName())
                .address(patientDto.getAddress())
                .phone(patientDto.getPhone())
                .messengerContact(patientDto.getMessengerContact())
                .birthDate(patientDto.getBirthDate())
                .preferentialCategory(patientDto.getPreferentialCategory())
                .build();
    }
}
