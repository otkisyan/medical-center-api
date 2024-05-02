package com.medicalcenter.receptionapi.controller;

import com.medicalcenter.receptionapi.dto.doctor.DoctorDto;
import com.medicalcenter.receptionapi.service.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("/doctors")
public class DoctorController {


    private final DoctorService doctorService;

    @GetMapping()
    public ResponseEntity<Page<DoctorDto>> findAllPatients(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "surname", required = false) String surname,
            @RequestParam(name = "middleName", required = false) String middleName,
            @RequestParam(name = "birthDate", required = false) LocalDate birthDate,
            @RequestParam(name = "medicalSpecialty", required = false) String medicalSpecialty,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {
            Page<DoctorDto> doctorsPage = doctorService.findAllDoctors(surname, name, middleName, birthDate, medicalSpecialty, page, pageSize);
            return ResponseEntity
                    .ok()
                    .body(doctorsPage);

    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> findPatientById(@PathVariable("id") Long id) {
        DoctorDto doctorDto = doctorService.findDoctorById(id);
        return ResponseEntity.ok()
                .body(doctorDto);
    }

    @PostMapping()
    public @ResponseBody ResponseEntity<DoctorDto> savePatient(@RequestBody DoctorDto doctorDto) {
        DoctorDto doctorResponseDto = doctorService.saveDoctor(doctorDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(doctorResponseDto.getId())
                .toUri();
        return ResponseEntity.created(location)
                .body(doctorResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDto> updatePatient(@RequestBody DoctorDto doctorDto, @PathVariable("id") Long id) {
        DoctorDto doctorResponseDto = doctorService.updateDoctor(doctorDto, id);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build()
                .toUri();
        return ResponseEntity.ok()
                .location(location)
                .body(doctorResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable("id") Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count")
    public long countDoctors(){
        return doctorService.count();
    }
}
