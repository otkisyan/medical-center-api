package com.medicalcenter.receptionapi.service;

import com.medicalcenter.receptionapi.domain.Patient;
import com.medicalcenter.receptionapi.dto.patient.PatientRequestDto;
import com.medicalcenter.receptionapi.dto.patient.PatientResponseDto;
import com.medicalcenter.receptionapi.exception.ResourceNotFoundException;
import com.medicalcenter.receptionapi.repository.PatientRepository;
import com.medicalcenter.receptionapi.specification.PatientSpecification;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public long count() {
        return patientRepository.count();
    }

    public List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }

    public Page<PatientResponseDto> findAllPatients(String surname,
                                                    String name,
                                                    String middleName,
                                                    LocalDate birthDate,
                                                    int page,
                                                    int pageSize) {

        Specification<Patient> specs = Specification.where(null);
        if (surname != null && !surname.isBlank()) {
            specs = specs.and(PatientSpecification.withSurname(surname));
        }
        if (name != null && !name.isBlank()) {
            specs = specs.and(PatientSpecification.withName(name));
        }
        if (middleName != null && !middleName.isBlank()) {
            specs = specs.and(PatientSpecification.withMiddleName(middleName));
        }
        if (birthDate != null) {
            specs = specs.and(PatientSpecification.withBirthDate(birthDate));
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        return patientRepository.findAll(specs, pageable).map(PatientResponseDto::ofEntity);
    }

    public PatientResponseDto findPatientById(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return PatientResponseDto.ofEntity(patient);
    }

    public PatientResponseDto savePatient(PatientRequestDto patientRequestDto) {
        Patient patient;
        try {
            patient = patientRepository.save(PatientRequestDto.toEntity(patientRequestDto));
        }
        catch (Exception ex){
            throw new IllegalArgumentException(ex.getMessage());
        }
        return PatientResponseDto.ofEntity(patient);
    }

    public PatientResponseDto updatePatient(PatientRequestDto patientRequestDto, Long id) {
        Patient patientToUpdate = patientRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        BeanUtils.copyProperties(patientRequestDto, patientToUpdate, "id", "appointments");
        Patient patient;
        try {
            patient = patientRepository.save(patientToUpdate);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
        return PatientResponseDto.ofEntity(patient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}