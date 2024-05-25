package com.medicalcenter.receptionapi.controller;

import com.medicalcenter.receptionapi.dto.consultation.ConsultationRequestDto;
import com.medicalcenter.receptionapi.dto.consultation.ConsultationResponseDto;
import com.medicalcenter.receptionapi.service.ConsultationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/consultations")
public class ConsultationController {

    private ConsultationService consultationService;

    @GetMapping("/{id}")
    public ResponseEntity<ConsultationResponseDto> findConsultationById(@PathVariable("id") Long appointmentId){
        ConsultationResponseDto consultationResponseDto = consultationService.findConsultationById(appointmentId);
        return ResponseEntity.ok(consultationResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultationResponseDto> updateConsultation(
            @PathVariable("id") Long appointmentId,
            @RequestBody ConsultationRequestDto consultationRequestDto){
        ConsultationResponseDto consultationResponseDto = consultationService.updateConsultation(appointmentId, consultationRequestDto);
        return ResponseEntity.ok(consultationResponseDto);
    }
}
