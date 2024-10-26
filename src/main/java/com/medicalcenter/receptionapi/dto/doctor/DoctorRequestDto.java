package com.medicalcenter.receptionapi.dto.doctor;

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
}
