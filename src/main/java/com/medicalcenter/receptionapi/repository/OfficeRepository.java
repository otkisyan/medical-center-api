package com.medicalcenter.receptionapi.repository;

import com.medicalcenter.receptionapi.domain.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OfficeRepository
    extends JpaRepository<Office, Long>, JpaSpecificationExecutor<Office> {}
