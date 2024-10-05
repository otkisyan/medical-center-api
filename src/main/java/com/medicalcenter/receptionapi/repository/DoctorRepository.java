package com.medicalcenter.receptionapi.repository;

import com.medicalcenter.receptionapi.domain.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DoctorRepository
    extends JpaRepository<Doctor, Long>, JpaSpecificationExecutor<Doctor> {}
