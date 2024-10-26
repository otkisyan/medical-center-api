package com.medicalcenter.receptionapi.dto.patient;

import jakarta.validation.constraints.NotNull;
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
public class PatientRequestDto implements Serializable {
  @NotNull private String name;
  @NotNull private String surname;
  @NotNull private String middleName;
  @NotNull private String address;
  @NotNull private String phone;
  private String messengerContact;
  @NotNull private LocalDate birthDate;
  private String preferentialCategory;
}
