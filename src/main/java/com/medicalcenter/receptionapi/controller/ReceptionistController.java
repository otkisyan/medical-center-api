package com.medicalcenter.receptionapi.controller;

import com.medicalcenter.receptionapi.dto.receptionist.ReceptionistRequestDto;
import com.medicalcenter.receptionapi.dto.receptionist.ReceptionistResponseDto;
import com.medicalcenter.receptionapi.dto.receptionist.ReceptionistResponseWithUserCredentialsDto;
import com.medicalcenter.receptionapi.service.ReceptionistService;
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
@RequestMapping("/receptionists")
public class ReceptionistController {

  private final ReceptionistService receptionistService;

  @GetMapping()
  public ResponseEntity<Page<ReceptionistResponseDto>> findAllReceptionists(
      @RequestParam(name = "name", required = false) String name,
      @RequestParam(name = "surname", required = false) String surname,
      @RequestParam(name = "middleName", required = false) String middleName,
      @RequestParam(name = "birthDate", required = false) LocalDate birthDate,
      @RequestParam(name = "page", defaultValue = "0") Integer page,
      @RequestParam(name = "pageSize", defaultValue = "5") @Max(10) Integer pageSize) {
    Page<ReceptionistResponseDto> receptionistsPage =
        receptionistService.findAllReceptionists(
            surname, name, middleName, birthDate, page, pageSize);
    return ResponseEntity.ok().body(receptionistsPage);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ReceptionistResponseDto> findReceptionistById(@PathVariable("id") Long id) {
    ReceptionistResponseDto receptionistResponseDto = receptionistService.findReceptionistById(id);
    return ResponseEntity.ok().body(receptionistResponseDto);
  }

  @PostMapping()
  public @ResponseBody ResponseEntity<ReceptionistResponseWithUserCredentialsDto> saveReceptionist(
      @RequestBody @Valid ReceptionistRequestDto receptionistRequestDto) {
    ReceptionistResponseWithUserCredentialsDto receptionistResponseWithUserCredentialsDto =
        receptionistService.saveReceptionist(receptionistRequestDto);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(
                receptionistResponseWithUserCredentialsDto.getReceptionistResponseDto().getId())
            .toUri();
    return ResponseEntity.created(location).body(receptionistResponseWithUserCredentialsDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ReceptionistResponseDto> updateReceptionist(
      @RequestBody @Valid ReceptionistRequestDto receptionistRequestDto,
      @PathVariable("id") Long id) {
    ReceptionistResponseDto receptionistResponseDto =
        receptionistService.updateReceptionist(receptionistRequestDto, id);
    return ResponseEntity.ok().body(receptionistResponseDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteReceptionist(@PathVariable("id") Long id) {
    receptionistService.deleteReceptionist(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/count")
  public long countReceptionists() {
    return receptionistService.count();
  }
}
