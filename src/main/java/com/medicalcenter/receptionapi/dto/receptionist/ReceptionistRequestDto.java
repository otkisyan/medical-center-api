package com.medicalcenter.receptionapi.dto.receptionist;

import com.medicalcenter.receptionapi.domain.Receptionist;
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

  public static Receptionist toEntity(ReceptionistRequestDto receptionistRequestDto) {
    return Receptionist.builder()
        .name(receptionistRequestDto.getName())
        .surname(receptionistRequestDto.getSurname())
        .middleName(receptionistRequestDto.getMiddleName())
        .birthDate(receptionistRequestDto.getBirthDate())
        .build();
  }
}
