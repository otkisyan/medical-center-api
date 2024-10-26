package com.medicalcenter.receptionapi.dto.dayofweek;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DayOfWeekRequestDto implements Serializable {

  private String name;
}
