package com.medicalcenter.receptionapi.controller;

import com.medicalcenter.receptionapi.dto.doctor.DoctorRequestDto;
import com.medicalcenter.receptionapi.dto.doctor.DoctorResponseDto;
import com.medicalcenter.receptionapi.dto.doctor.DoctorResponseWithUserCredentialsDto;
import com.medicalcenter.receptionapi.dto.workschedule.WorkScheduleResponseDto;
import com.medicalcenter.receptionapi.service.DoctorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import java.net.URI;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@AllArgsConstructor
@RequestMapping("/doctors")
public class DoctorController {

  private final DoctorService doctorService;

  @GetMapping()
  public ResponseEntity<Page<DoctorResponseDto>> findAllDoctors(
      @RequestParam(name = "name", required = false) String name,
      @RequestParam(name = "surname", required = false) String surname,
      @RequestParam(name = "middleName", required = false) String middleName,
      @RequestParam(name = "birthDate", required = false) LocalDate birthDate,
      @RequestParam(name = "medicalSpecialty", required = false) String medicalSpecialty,
      @RequestParam(name = "office", required = false) Integer officeNumber,
      @RequestParam(name = "page", defaultValue = "0") Integer page,
      @RequestParam(name = "pageSize", defaultValue = "5") @Max(10) Integer pageSize) {
    Page<DoctorResponseDto> doctorsPage =
        doctorService.findAllDoctors(
            surname, name, middleName, birthDate, medicalSpecialty, officeNumber, page, pageSize);
    return ResponseEntity.ok().body(doctorsPage);
  }

  @GetMapping("/{id}/work-schedules")
  public ResponseEntity<Page<WorkScheduleResponseDto>> findDoctorWorkSchedules(
      @PathVariable("id") Long id,
      @RequestParam(name = "page", defaultValue = "0") Integer page,
      @RequestParam(name = "pageSize", defaultValue = "7") @Max(10) Integer pageSize) {
    Page<WorkScheduleResponseDto> workScheduleResponseDtoList =
        doctorService.findDoctorWorkSchedules(page, pageSize, id);
    return ResponseEntity.ok(workScheduleResponseDtoList);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DoctorResponseDto> findDoctorById(@PathVariable("id") Long id) {
    DoctorResponseDto doctorResponseDto = doctorService.findDoctorById(id);
    return ResponseEntity.ok().body(doctorResponseDto);
  }

  @PostMapping()
  public @ResponseBody ResponseEntity<DoctorResponseWithUserCredentialsDto> saveDoctor(
      @RequestBody @Valid DoctorRequestDto doctorRequestDto) {
    DoctorResponseWithUserCredentialsDto doctorResponseWithUserCredentialsDto =
        doctorService.saveDoctor(doctorRequestDto);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(doctorResponseWithUserCredentialsDto.getDoctorResponseDto().getId())
            .toUri();
    return ResponseEntity.created(location).body(doctorResponseWithUserCredentialsDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<DoctorResponseDto> updateDoctor(
      @RequestBody @Valid DoctorRequestDto doctorRequestDto, @PathVariable("id") Long id) {
    DoctorResponseDto doctorResponseDto = doctorService.updateDoctor(doctorRequestDto, id);
    return ResponseEntity.ok().body(doctorResponseDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDoctor(@PathVariable("id") Long id) {
    doctorService.deleteDoctor(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/count")
  public long countDoctors() {
    return doctorService.count();
  }
}
