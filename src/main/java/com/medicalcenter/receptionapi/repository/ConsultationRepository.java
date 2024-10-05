package com.medicalcenter.receptionapi.repository;

import com.medicalcenter.receptionapi.domain.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ConsultationRepository
    extends JpaRepository<Consultation, Long>, JpaSpecificationExecutor<Consultation> {}
