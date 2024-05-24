package com.medicalcenter.receptionapi.controller;

import com.medicalcenter.receptionapi.dto.appointment.AppointmentRequestDto;
import com.medicalcenter.receptionapi.dto.appointment.AppointmentResponseDto;
import com.medicalcenter.receptionapi.dto.appointment.TimeSlotDto;
import com.medicalcenter.receptionapi.service.AppointmentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {

    private AppointmentService appointmentService;

    @GetMapping()
    public ResponseEntity<Page<AppointmentResponseDto>> findAllAppointments(
            @RequestParam(name = "patient", required = false) String patient,
            @RequestParam(name = "doctor", required = false) String doctor,
            @RequestParam(name = "date", required = false) LocalDate date,
            @RequestParam(name = "timeStart", required = false) LocalTime timeStart,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "5") @Max(10) Integer pageSize) {

        Page<AppointmentResponseDto> appointmentsPage =
                appointmentService.findAllAppointments(patient, doctor, date, timeStart, page, pageSize);
        return ResponseEntity.ok().body(appointmentsPage);
    }

    @GetMapping("/timetable/{id}")
    public ResponseEntity<List<TimeSlotDto>> getTimeTable(@PathVariable("id") Long doctorId,
                                                          @RequestParam(name = "date") LocalDate date) {
        List<TimeSlotDto> timeSlots = appointmentService.generateTimetable(doctorId, date);
        return ResponseEntity.ok(timeSlots);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDto> findAppointmentById(@PathVariable("id") Long id) {
        AppointmentResponseDto appointmentResponseDto = appointmentService.findAppointmentById(id);
        return ResponseEntity.ok().body(appointmentResponseDto);
    }

    @PostMapping()
    public @ResponseBody ResponseEntity<AppointmentResponseDto> saveAppointment(
            @RequestBody @Valid AppointmentRequestDto appointmentRequestDto) {
        AppointmentResponseDto appointmentResponseDto = appointmentService.saveAppointment(appointmentRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(appointmentResponseDto.getId())
                .toUri();
        return ResponseEntity.created(location)
                .body(appointmentResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponseDto> updateAppointment(
            @RequestBody AppointmentRequestDto appointmentRequestDto,
            @PathVariable("id") Long id) {
       AppointmentResponseDto appointmentResponseDto = appointmentService.updateAppointment(appointmentRequestDto, id);
        return ResponseEntity.ok().body(appointmentResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable("id") Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count")
    public long countAppointments() {
        return appointmentService.count();
    }
}
