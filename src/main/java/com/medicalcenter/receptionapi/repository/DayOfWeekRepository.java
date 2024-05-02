package com.medicalcenter.receptionapi.repository;

import com.medicalcenter.receptionapi.domain.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayOfWeekRepository extends JpaRepository<DayOfWeek, Integer> {
}