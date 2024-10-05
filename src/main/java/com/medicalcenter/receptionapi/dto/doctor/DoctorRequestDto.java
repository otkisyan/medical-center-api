package com.medicalcenter.receptionapi.dto.doctor;

import com.medicalcenter.receptionapi.domain.Doctor;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DoctorRequestDto implements Serializable {
  @NotNull private String name;
  @NotNull private String surname;
  @NotNull private String middleName;
  @NotNull private String address;
  @NotNull private String phone;
  private String messengerContact;
  @NotNull private LocalDate birthDate;
  @NotNull private String medicalSpecialty;
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
