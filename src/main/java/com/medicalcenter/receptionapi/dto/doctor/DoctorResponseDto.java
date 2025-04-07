package com.medicalcenter.receptionapi.dto.doctor;

import com.medicalcenter.receptionapi.dto.office.OfficeResponseDto;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

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
  private String education;
  private LocalDate birthDate;
  private String medicalSpecialty;
  private String qualificationCategory;
  private OfficeResponseDto office;
}
