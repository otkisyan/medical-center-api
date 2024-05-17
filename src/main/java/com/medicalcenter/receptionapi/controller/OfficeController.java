package com.medicalcenter.receptionapi.controller;

import com.medicalcenter.receptionapi.dto.office.OfficeRequestDto;
import com.medicalcenter.receptionapi.dto.office.OfficeResponseDto;
import com.medicalcenter.receptionapi.service.OfficeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/offices")
public class OfficeController {

    private final OfficeService officeService;

    @GetMapping()
    public ResponseEntity<Page<OfficeResponseDto>> findAllOffices(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "number", required = false) Integer number,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "5") @Max(10) Integer pageSize) {
        Page<OfficeResponseDto> officesPage = officeService.findAllOffices(name, number, page, pageSize);
        return ResponseEntity.ok().body(officesPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfficeResponseDto> findOfficeById(@PathVariable("id") Long id) {
        OfficeResponseDto officeResponseDto = officeService.findOfficeById(id);
        return ResponseEntity.ok().body(officeResponseDto);
    }

    @PostMapping()
    public ResponseEntity<OfficeResponseDto> saveOffice(@RequestBody @Valid OfficeRequestDto officeRequestDto) {
        OfficeResponseDto officeResponseDto = officeService.saveOffice(officeRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(officeResponseDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(officeResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfficeResponseDto> updateOffice(@RequestBody @Valid OfficeRequestDto officeRequestDto, @PathVariable("id") Long id) {
        OfficeResponseDto officeResponseDto = officeService.updateOffice(officeRequestDto, id);
        return ResponseEntity.ok().body(officeResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffice(@PathVariable("id") Long id) {
        officeService.deleteOffice(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count")
    public long countOffices() {
        return officeService.count();
    }
}
