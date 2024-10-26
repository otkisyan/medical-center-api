package com.medicalcenter.receptionapi.service;

import com.medicalcenter.receptionapi.domain.Doctor;
import com.medicalcenter.receptionapi.domain.Office;
import com.medicalcenter.receptionapi.dto.doctor.DoctorRequestDto;
import com.medicalcenter.receptionapi.dto.doctor.DoctorResponseDto;
import com.medicalcenter.receptionapi.dto.doctor.DoctorResponseWithUserCredentialsDto;
import com.medicalcenter.receptionapi.dto.user.RegisterRequestDto;
import com.medicalcenter.receptionapi.dto.user.UserCredentialsDto;
import com.medicalcenter.receptionapi.dto.workschedule.WorkScheduleResponseDto;
import com.medicalcenter.receptionapi.exception.ResourceNotFoundException;
import com.medicalcenter.receptionapi.mapper.DoctorMapper;
import com.medicalcenter.receptionapi.repository.DoctorRepository;
import com.medicalcenter.receptionapi.repository.OfficeRepository;
import com.medicalcenter.receptionapi.security.enums.RoleAuthority;
import com.medicalcenter.receptionapi.specification.DoctorSpecification;
import java.time.LocalDate;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DoctorService {

  private final DoctorRepository doctorRepository;
  private final OfficeRepository officeRepository;
  private final WorkScheduleService workScheduleService;
  private final UserService userService;
  private final DoctorMapper doctorMapper;

  @Cacheable(value = "doctors", key = "'count'")
  public long count() {
    return doctorRepository.count();
  }

  @Cacheable(
      value = "doctors",
      key =
          "#surname + '_' + #name + '_' + #middleName + '_'"
              + "+ (#birthDate != null ? #birthDate.toString() : 'null' ) + '_'"
              + "+ #medicalSpecialty + '_' + #officeNumber + '_' + #page + '_' + #pageSize")
  public Page<DoctorResponseDto> findAllDoctors(
      String surname,
      String name,
      String middleName,
      LocalDate birthDate,
      String medicalSpecialty,
      Integer officeNumber,
      int page,
      int pageSize) {
    Specification<Doctor> specs = Specification.where(null);
    if (surname != null && !surname.isBlank()) {
      specs = specs.and(DoctorSpecification.withSurname(surname));
    }
    if (name != null && !name.isBlank()) {
      specs = specs.and(DoctorSpecification.withName(name));
    }
    if (middleName != null && !middleName.isBlank()) {
      specs = specs.and(DoctorSpecification.withMiddleName(middleName));
    }
    if (medicalSpecialty != null && !medicalSpecialty.isBlank()) {
      specs = specs.and(DoctorSpecification.withMedicalSpeciality(medicalSpecialty));
    }
    if (birthDate != null) {
      specs = specs.and(DoctorSpecification.withBirthDate(birthDate));
    }
    if (officeNumber != null) {
      specs = specs.and(DoctorSpecification.withOffice(officeNumber));
    }
    Sort sort = Sort.by(Sort.Direction.DESC, "id");
    Pageable pageable = PageRequest.of(page, pageSize, sort);
    return doctorRepository.findAll(specs, pageable).map(doctorMapper::doctorToDoctorResponseDto);
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST') or #id == authentication.principal.id")
  @Cacheable(value = "doctors", key = "#id")
  public DoctorResponseDto findDoctorById(Long id) {
    return doctorRepository
        .findById(id)
        .map(doctorMapper::doctorToDoctorResponseDto)
        .orElseThrow(ResourceNotFoundException::new);
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST') or #doctorId == authentication.principal.id")
  public Page<WorkScheduleResponseDto> findDoctorWorkSchedules(
      int page, int pageSize, Long doctorId) {
    doctorRepository.findById(doctorId).orElseThrow(ResourceNotFoundException::new);
    return workScheduleService.findWorkSchedulesByDoctorId(page, pageSize, doctorId);
  }

  @CacheEvict(value = "doctors", allEntries = true)
  public DoctorResponseWithUserCredentialsDto saveDoctor(DoctorRequestDto doctorRequestDto) {
    Doctor doctor = doctorMapper.doctorRequestDtoToDoctor(doctorRequestDto);
    if (doctorRequestDto.getOfficeId() != null) {
      Optional<Office> officeOptional = officeRepository.findById(doctorRequestDto.getOfficeId());
      if (officeOptional.isPresent()) {
        Office office = officeOptional.get();
        doctor.setOffice(office);
      }
    }
    UserCredentialsDto userCredentialsDto = userService.generateRandomCredentials();
    RegisterRequestDto registerRequestDto =
        RegisterRequestDto.builder()
            .userCredentialsDto(userCredentialsDto)
            .role(RoleAuthority.DOCTOR)
            .build();
    userService.saveUser(registerRequestDto, doctor);
    doctor = doctorRepository.save(doctor);
    return DoctorResponseWithUserCredentialsDto.builder()
        .doctorResponseDto(doctorMapper.doctorToDoctorResponseDto(doctor))
        .userCredentialsDto(userCredentialsDto)
        .build();
  }

  @CacheEvict(value = "doctors", allEntries = true)
  public DoctorResponseDto updateDoctor(DoctorRequestDto doctorRequestDto, Long id) {
    Doctor doctorToUpdate =
        doctorRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    Doctor updateRequestDoctor = doctorMapper.doctorRequestDtoToDoctor(doctorRequestDto);
    BeanUtils.copyProperties(
        updateRequestDoctor,
        doctorToUpdate,
        "id",
        "appointments",
        "office",
        "user",
        "workSchedules");
    if (doctorRequestDto.getOfficeId() != null) {
      Optional<Office> officeOptional = officeRepository.findById(doctorRequestDto.getOfficeId());
      if (officeOptional.isPresent()) {
        Office office = officeOptional.get();
        doctorToUpdate.setOffice(office);
      }
    } else {
      doctorToUpdate.setOffice(null);
    }
    Doctor updatedDoctor = doctorRepository.save(doctorToUpdate);
    return doctorMapper.doctorToDoctorResponseDto(updatedDoctor);
  }

  @CacheEvict(value = "doctors", allEntries = true)
  public void deleteDoctor(Long id) {
    doctorRepository.deleteById(id);
  }
}
