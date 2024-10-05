package com.medicalcenter.receptionapi.dto.dayofweek;

import com.medicalcenter.receptionapi.domain.DayOfWeek;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DayOfWeekResponseDto implements Serializable {
  private Integer id;
  private String name;

  public static DayOfWeekResponseDto ofEntity(DayOfWeek dayOfWeek) {
    return DayOfWeekResponseDto.builder().id(dayOfWeek.getId()).name(dayOfWeek.getName()).build();
  }
}
