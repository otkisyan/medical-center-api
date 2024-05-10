package com.medicalcenter.receptionapi.service;

import com.medicalcenter.receptionapi.domain.Doctor;
import com.medicalcenter.receptionapi.dto.doctor.DoctorRequestDto;
import com.medicalcenter.receptionapi.dto.doctor.DoctorResponseDto;
import com.medicalcenter.receptionapi.exception.ResourceNotFoundException;
import com.medicalcenter.receptionapi.repository.DoctorRepository;
import com.medicalcenter.receptionapi.specification.DoctorSpecification;
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
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public long count() {
        return doctorRepository.count();
    }

    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
    }

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

    public DoctorResponseDto findDoctorById(Long id) {
        return doctorRepository.findById(id).map(DoctorResponseDto::ofEntity).orElseThrow(ResourceNotFoundException::new);
    }

    public DoctorResponseDto saveDoctor(DoctorRequestDto doctorRequestDto) {
        Doctor doctor = doctorRepository.save(DoctorRequestDto.toEntity(doctorRequestDto));
        return DoctorResponseDto.ofEntity(doctor);
    }

    public DoctorResponseDto updateDoctor(DoctorRequestDto doctorRequestDto, Long id) {
        Doctor doctorToUpdate = doctorRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        BeanUtils.copyProperties(doctorRequestDto, doctorToUpdate, "id", "appointments", "office");
        Doctor doctor = doctorRepository.save(doctorToUpdate);
        return DoctorResponseDto.ofEntity(doctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
}

