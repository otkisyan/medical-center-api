package com.medicalcenter.receptionapi.dto.receptionist;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceptionistRequestDto implements Serializable {

  @NotNull private String name;
  @NotNull private String surname;
  @NotNull private String middleName;
  @NotNull private LocalDate birthDate;
}
