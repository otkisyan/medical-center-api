package com.medicalcenter.receptionapi.repository;

import com.medicalcenter.receptionapi.domain.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, Long> {
    WorkSchedule findByDoctor_IdAndDayOfWeek_Id(Long id, Integer id1);
}