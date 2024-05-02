package com.medicalcenter.receptionapi.repository;

import com.medicalcenter.receptionapi.domain.Patient;
import com.medicalcenter.receptionapi.domain.Receptionist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReceptionistRepository extends JpaRepository<Receptionist, Long>, JpaSpecificationExecutor<Receptionist> {
}