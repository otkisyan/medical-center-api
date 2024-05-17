package com.medicalcenter.receptionapi.controller;

import com.medicalcenter.receptionapi.dto.patient.PatientRequestDto;
import com.medicalcenter.receptionapi.dto.patient.PatientResponseDto;
import com.medicalcenter.receptionapi.service.PatientService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    @GetMapping()
    public ResponseEntity<Page<PatientResponseDto>> findAllPatients(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "surname", required = false) String surname,
            @RequestParam(name = "middleName", required = false) String middleName,
            @RequestParam(name = "birthDate", required = false) LocalDate birthDate,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "5") @Max(10) Integer pageSize) {
        Page<PatientResponseDto> patientsPage =
                patientService.findAllPatients(surname, name, middleName, birthDate, page, pageSize);
        return ResponseEntity
                .ok()
                .body(patientsPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDto> findPatientById(@PathVariable("id") Long id) {
        PatientResponseDto patientResponseDto = patientService.findPatientById(id);
        return ResponseEntity.ok()
                .body(patientResponseDto);
    }

    @PostMapping()
    public @ResponseBody ResponseEntity<PatientResponseDto> savePatient(@RequestBody @Valid PatientRequestDto patientRequestDto) {
        PatientResponseDto patientResponseDto = patientService.savePatient(patientRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(patientResponseDto.getId())
                .toUri();
        return ResponseEntity.created(location)
                .body(patientResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDto> updatePatient(@RequestBody @Valid PatientRequestDto patientRequestDto, @PathVariable("id") Long id) {
        PatientResponseDto patientResponseDto = patientService.updatePatient(patientRequestDto, id);
        return ResponseEntity.ok()
                .body(patientResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable("id") Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count")
    public long countPatients() {
        return patientService.count();
    }
}
