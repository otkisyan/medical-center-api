package com.medicalcenter.receptionapi.repository;

import com.medicalcenter.receptionapi.domain.Office;
import com.medicalcenter.receptionapi.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface OfficeRepository extends JpaRepository<Office, Long>, JpaSpecificationExecutor<Office> {
    List<Office> findByDoctorNull();
}