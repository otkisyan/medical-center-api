package com.medicalcenter.receptionapi.dto.doctor;

import com.medicalcenter.receptionapi.domain.Doctor;
import com.medicalcenter.receptionapi.dto.office.OfficeDto;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Data
public class DoctorDto implements Serializable {

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
    private OfficeDto office;

    public static DoctorDto ofEntity(Doctor doctor) {
        return DoctorDto.builder()
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
                .office(OfficeDto.ofEntity(doctor.getOffice()))
                .build();
    }

    public static Doctor toEntity(DoctorDto doctorDto) {
        return Doctor.builder()
                .name(doctorDto.getName())
                .surname(doctorDto.getSurname())
                .middleName(doctorDto.getMiddleName())
                .address(doctorDto.getAddress())
                .phone(doctorDto.getPhone())
                .messengerContact(doctorDto.getMessengerContact())
                .birthDate(doctorDto.getBirthDate())
                .medicalSpecialty(doctorDto.getMedicalSpecialty())
                .qualificationCategory(doctorDto.getQualificationCategory())
                .build();
    }
}
