package com.medicalcenter.receptionapi.dto.patient;

import com.medicalcenter.receptionapi.domain.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponseDto implements Serializable {
    private Long id;
    private String name;
    private String surname;
    private String middleName;
    private String address;
    private String phone;
    private String messengerContact;
    private LocalDate birthDate;
    private String preferentialCategory;

    public static PatientResponseDto ofEntity(Patient patient) {
        return PatientResponseDto.builder()
                .id(patient.getId())
                .name(patient.getName())
                .surname(patient.getSurname())
                .middleName(patient.getMiddleName())
                .address(patient.getAddress())
                .phone(patient.getPhone())
                .messengerContact(patient.getMessengerContact())
                .birthDate(patient.getBirthDate())
                .preferentialCategory(patient.getPreferentialCategory())
                .build();
    }
}
