package com.medicalcenter.receptionapi.controller;

import com.medicalcenter.receptionapi.dto.workschedule.WorkScheduleRequestDto;
import com.medicalcenter.receptionapi.dto.workschedule.WorkScheduleResponseDto;
import com.medicalcenter.receptionapi.service.WorkScheduleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/work-schedules")
public class WorkScheduleController {

    private final WorkScheduleService workScheduleService;

    @PutMapping("/{id}")
    public ResponseEntity<WorkScheduleResponseDto> updateWorkSchedule(@RequestBody @Valid WorkScheduleRequestDto workScheduleRequestDto,
                                                                      @PathVariable("id") Long id) {
        WorkScheduleResponseDto workScheduleResponseDto = workScheduleService.updateWorkSchedule(workScheduleRequestDto, id);
        return ResponseEntity.ok(workScheduleResponseDto);
    }
}
