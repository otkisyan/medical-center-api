package com.medicalcenter.receptionapi.dto.patient;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
