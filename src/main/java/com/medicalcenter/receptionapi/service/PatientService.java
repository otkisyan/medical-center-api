package com.medicalcenter.receptionapi.service;

import com.medicalcenter.receptionapi.domain.Patient;
import com.medicalcenter.receptionapi.dto.patient.PatientRequestDto;
import com.medicalcenter.receptionapi.dto.patient.PatientResponseDto;
import com.medicalcenter.receptionapi.exception.ResourceNotFoundException;
import com.medicalcenter.receptionapi.mapper.PatientMapper;
import com.medicalcenter.receptionapi.repository.PatientRepository;
import com.medicalcenter.receptionapi.specification.PatientSpecification;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PatientService {

  private final PatientRepository patientRepository;
  private final PatientMapper patientMapper;

  @Cacheable(value = "patients", key = "'count'")
  public long count() {
    return patientRepository.count();
  }

  @Cacheable(
      value = "patients",
      key =
          "#surname + '_' + #name + '_' + #middleName + '_'"
              + "+ (#birthDate != null ? #birthDate.toString() : 'null' )"
              + "+ '_' + #page + '_' + #pageSize")
  public Page<PatientResponseDto> findAllPatients(
      String surname, String name, String middleName, LocalDate birthDate, int page, int pageSize) {

    Specification<Patient> spec = Specification.where(null);
    if (surname != null && !surname.isBlank()) {
      spec = spec.and(PatientSpecification.withSurname(surname));
    }
    if (name != null && !name.isBlank()) {
      spec = spec.and(PatientSpecification.withName(name));
    }
    if (middleName != null && !middleName.isBlank()) {
      spec = spec.and(PatientSpecification.withMiddleName(middleName));
    }
    if (birthDate != null) {
      spec = spec.and(PatientSpecification.withBirthDate(birthDate));
    }
    Sort sort = Sort.by(Sort.Direction.DESC, "id");
    Pageable pageable = PageRequest.of(page, pageSize, sort);
    return patientRepository
        .findAll(spec, pageable)
        .map(patientMapper::patientToPatientResponseDto);
  }

  public List<Patient> findAllPatients() {
    return patientRepository.findAll();
  }

  @Cacheable(value = "patients", key = "#id")
  public PatientResponseDto findPatientById(Long id) {
    Patient patient = patientRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    return patientMapper.patientToPatientResponseDto(patient);
  }

  @CacheEvict(value = "patients", allEntries = true)
  public PatientResponseDto savePatient(PatientRequestDto patientRequestDto) {
    Patient patient =
        patientRepository.save(patientMapper.patientRequestDtoToPatient(patientRequestDto));
    return patientMapper.patientToPatientResponseDto(patient);
  }

  @CacheEvict(value = "patients", allEntries = true)
  public PatientResponseDto updatePatient(PatientRequestDto patientRequestDto, Long id) {
    Patient patientToUpdate =
        patientRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    BeanUtils.copyProperties(patientRequestDto, patientToUpdate, "id", "appointments");
    Patient patient = patientRepository.save(patientToUpdate);
    return patientMapper.patientToPatientResponseDto(patient);
  }

  @CacheEvict(value = "patients", allEntries = true)
  public void deletePatient(Long id) {
    patientRepository.deleteById(id);
  }
}
