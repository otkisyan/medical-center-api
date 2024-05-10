package com.medicalcenter.receptionapi.dto.doctor;

import com.medicalcenter.receptionapi.domain.Doctor;
import com.medicalcenter.receptionapi.dto.office.OfficeResponseDto;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Data
public class DoctorResponseDto implements Serializable {

    private Long id;
    private String name;
    private String surname;
    private String middleName;
    private String address;
    private String phone;
    private String messengerContact;
    private LocalDate birthDate;
    private String medicalSpecialty;
    private String qualificationCategory;
    private OfficeResponseDto office;

    public static DoctorResponseDto ofEntity(Doctor doctor) {
        return DoctorResponseDto.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .surname(doctor.getSurname())
                .middleName(doctor.getMiddleName())
                .address(doctor.getAddress())
                .phone(doctor.getPhone())
                .messengerContact(doctor.getMessengerContact())
                .birthDate(doctor.getBirthDate())
                .medicalSpecialty(doctor.getMedicalSpecialty())
                .qualificationCategory(doctor.getQualificationCategory())
                .office(OfficeResponseDto.ofEntity(doctor.getOffice()))
                .build();
    }
}
