package com.medicalcenter.receptionapi.service;

import com.medicalcenter.receptionapi.domain.Doctor;
import com.medicalcenter.receptionapi.domain.Office;
import com.medicalcenter.receptionapi.domain.User;
import com.medicalcenter.receptionapi.dto.doctor.DoctorRequestDto;
import com.medicalcenter.receptionapi.dto.doctor.DoctorResponseDto;
import com.medicalcenter.receptionapi.dto.doctor.DoctorResponseWithUserCredentialsDto;
import com.medicalcenter.receptionapi.dto.user.RegisterRequestDto;
import com.medicalcenter.receptionapi.dto.user.UserCredentialsDto;
import com.medicalcenter.receptionapi.dto.user.UserDetailsDto;
import com.medicalcenter.receptionapi.dto.workschedule.WorkScheduleResponseDto;
import com.medicalcenter.receptionapi.exception.ResourceNotFoundException;
import com.medicalcenter.receptionapi.repository.DoctorRepository;
import com.medicalcenter.receptionapi.repository.OfficeRepository;
import com.medicalcenter.receptionapi.repository.UserRepository;
import com.medicalcenter.receptionapi.security.enums.RoleAuthority;
import com.medicalcenter.receptionapi.specification.DoctorSpecification;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final OfficeRepository officeRepository;
    private final UserRepository userRepository;
    private final WorkScheduleService workScheduleService;
    private final UserService userService;


    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
    }

    @Cacheable(value = "doctors", key = "'count'")
    public long count() {
        return doctorRepository.count();
    }

    @Cacheable(value = "doctors", key = "#surname + '_' + #name + '_' + #middleName + '_'" +
            "+ (#birthDate != null ? #birthDate.toString() : 'null' ) + '_' + #medicalSpecialty + '_'  + #page + '_' + #pageSize")
    public Page<DoctorResponseDto> findAllDoctors(String name,
                                                  String surname,
                                                  String middleName,
                                                  LocalDate birthDate,
                                                  String medicalSpecialty,
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
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        return doctorRepository.findAll(specs, pageable).map(DoctorResponseDto::ofEntity);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST') or #id == authentication.principal.id")
    @Cacheable(value = "doctors", key = "#id")
    public DoctorResponseDto findDoctorById(Long id) {
        return doctorRepository.findById(id).map(DoctorResponseDto::ofEntity).orElseThrow(ResourceNotFoundException::new);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST') or #doctorId == authentication.principal.id")
    public Page<WorkScheduleResponseDto> findDoctorWorkSchedules(int page, int pageSize, Long doctorId){
        findDoctorById(doctorId);
        return workScheduleService.findWorkSchedulesByDoctorId(page, pageSize, doctorId);
    }

    @CacheEvict(value = "doctors", allEntries = true)
    public DoctorResponseWithUserCredentialsDto saveDoctor(DoctorRequestDto doctorRequestDto) {
        Doctor doctor = DoctorRequestDto.toEntity(doctorRequestDto);
        if(doctorRequestDto.getOfficeId() != null) {
            Optional<Office> officeOptional = officeRepository.findById(doctorRequestDto.getOfficeId());
            if (officeOptional.isPresent()) {
                Office office = officeOptional.get();
                doctor.setOffice(office);
            }
        }
        UserCredentialsDto userCredentialsDto = userService.generateRandomCredentials();
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .userCredentialsDto(userCredentialsDto)
                .role(RoleAuthority.DOCTOR)
                .build();
        UserDetailsDto userDetailsDto = userService.saveUser(registerRequestDto);
        Optional<User> user = userRepository.findByUsername(userDetailsDto.getUsername());
        if (user.isPresent()) {
            doctor.setUser(user.get());
        }
        doctor = doctorRepository.save(doctor);
        workScheduleService.createDoctorEmptyWorkSchedules(doctor);
        return DoctorResponseWithUserCredentialsDto.builder()
                .doctorResponseDto(DoctorResponseDto.ofEntity(doctor))
                .userCredentialsDto(userCredentialsDto)
                .build();
    }

    @CacheEvict(value = "doctors", allEntries = true)
    public DoctorResponseDto updateDoctor(DoctorRequestDto doctorRequestDto, Long id) {
        Doctor doctorToUpdate = doctorRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        Doctor updateRequestDoctor = DoctorRequestDto.toEntity(doctorRequestDto);
        BeanUtils.copyProperties(updateRequestDoctor, doctorToUpdate, "id", "appointments", "office", "user", "workSchedules");
        if(doctorRequestDto.getOfficeId() != null) {
            Optional<Office> officeOptional = officeRepository.findById(doctorRequestDto.getOfficeId());
            if (officeOptional.isPresent()) {
                Office office = officeOptional.get();
                doctorToUpdate.setOffice(office);
            }
        }
        else {
            doctorToUpdate.setOffice(null);
        }
        Doctor updatedDoctor = doctorRepository.save(doctorToUpdate);
        return DoctorResponseDto.ofEntity(updatedDoctor);
    }

    @CacheEvict(value = "doctors", allEntries = true)
    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
}

