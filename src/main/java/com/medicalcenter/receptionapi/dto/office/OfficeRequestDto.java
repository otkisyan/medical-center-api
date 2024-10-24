package com.medicalcenter.receptionapi.dto.office;

import com.medicalcenter.receptionapi.domain.Office;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfficeRequestDto implements Serializable {

  @NotNull private Integer number;
  @NotNull private String name;

  public static Office toEntity(OfficeRequestDto officeRequestDto) {
    return Office.builder()
        .name(officeRequestDto.getName())
        .number(officeRequestDto.getNumber())
        .build();
  }

  public static OfficeResponseDto ofEntity(Office office) {
    if (office == null) {
      return null;
    }
    return OfficeResponseDto.builder()
        .id(office.getId())
        .name(office.getName())
        .number(office.getNumber())
        .build();
  }
}
