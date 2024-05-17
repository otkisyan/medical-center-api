package com.medicalcenter.receptionapi.dto.patient;

import com.medicalcenter.receptionapi.domain.Patient;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequestDto {
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String middleName;
    @NotNull
    private String address;
    @NotNull
    private String phone;
    private String messengerContact;
    @NotNull
    private LocalDate birthDate;
    private String preferentialCategory;

    public static Patient toEntity(PatientRequestDto patientRequestDto) {
        return Patient.builder()
                .name(patientRequestDto.getName())
                .surname(patientRequestDto.getSurname())
                .middleName(patientRequestDto.getMiddleName())
                .address(patientRequestDto.getAddress())
                .phone(patientRequestDto.getPhone())
                .messengerContact(patientRequestDto.getMessengerContact())
                .birthDate(patientRequestDto.getBirthDate())
                .preferentialCategory(patientRequestDto.getPreferentialCategory())
                .build();
    }
}
