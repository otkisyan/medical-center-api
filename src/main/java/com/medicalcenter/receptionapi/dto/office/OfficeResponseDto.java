package com.medicalcenter.receptionapi.dto.office;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OfficeResponseDto implements Serializable {
  private Long id;
  private Integer number;
  private String name;
}
