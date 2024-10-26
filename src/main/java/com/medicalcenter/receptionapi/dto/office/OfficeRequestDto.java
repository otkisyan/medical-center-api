package com.medicalcenter.receptionapi.dto.office;

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
}
