package com.medicalcenter.receptionapi.dto.workschedule;

import com.medicalcenter.receptionapi.domain.WorkSchedule;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkScheduleRequestDto {
    private Long doctorId;
    private Long dayOfWeekId;
    private LocalTime workTimeStart;
    private LocalTime workTimeEnd;

    public static WorkSchedule toEntity(WorkScheduleRequestDto workScheduleRequestDto){
       return WorkSchedule.builder()
               .workTimeStart(workScheduleRequestDto.getWorkTimeStart())
               .workTimeEnd(workScheduleRequestDto.getWorkTimeEnd())
               .build();
    }
}
