package com.medicalcenter.receptionapi.dto.office;

import com.medicalcenter.receptionapi.domain.Office;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OfficeResponseDto implements Serializable {
  private Long id;
  private Integer number;
  private String name;

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
