package com.medicalcenter.receptionapi.dto.dayofweek;

import com.medicalcenter.receptionapi.domain.DayOfWeek;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DayOfWeekRequestDto implements Serializable {

    private String name;

    public static DayOfWeek toEntity(DayOfWeekRequestDto dayOfWeekRequestDto){
        return DayOfWeek.builder()
                .name(dayOfWeekRequestDto.getName()).build();
    }
}
