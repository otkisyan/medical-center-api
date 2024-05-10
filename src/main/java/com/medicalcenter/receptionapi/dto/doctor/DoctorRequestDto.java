package com.medicalcenter.receptionapi.dto.doctor;

import com.medicalcenter.receptionapi.domain.Doctor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Data
public class DoctorRequestDto implements Serializable {
    private String name;
    private String surname;
    private String middleName;
    private String address;
    private String phone;
    private String messengerContact;
    private LocalDate birthDate;
    private String medicalSpecialty;
    private String qualificationCategory;
    private Long officeId;

    public static Doctor toEntity(DoctorRequestDto doctorRequestDto) {
        return Doctor.builder()
                .name(doctorRequestDto.getName())
                .surname(doctorRequestDto.getSurname())
                .middleName(doctorRequestDto.getMiddleName())
                .address(doctorRequestDto.getAddress())
                .phone(doctorRequestDto.getPhone())
                .messengerContact(doctorRequestDto.getMessengerContact())
                .birthDate(doctorRequestDto.getBirthDate())
                .medicalSpecialty(doctorRequestDto.getMedicalSpecialty())
                .qualificationCategory(doctorRequestDto.getQualificationCategory())
                .build();
    }
}
