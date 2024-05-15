package com.medicalcenter.receptionapi.repository;

import com.medicalcenter.receptionapi.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>, JpaSpecificationExecutor<Appointment> {
    List<Appointment> findByDoctor_IdAndDate(Long id, LocalDate date);

    boolean existsByPatient_IdAndDateAndTimeStartLessThanEqualAndTimeEndGreaterThanEqual(
            Long id, LocalDate date, LocalTime timeStart, LocalTime timeEnd);

    boolean existsByDoctor_IdAndDateAndTimeStartLessThanEqualAndTimeEndGreaterThanEqual(
            Long id, LocalDate date, LocalTime timeStart, LocalTime timeEnd);

    boolean existsByDoctor_IdAndDateAndTimeStartLessThanEqualAndTimeEndGreaterThanEqualAndIdNot(
            Long id, LocalDate date, LocalTime timeStart, LocalTime timeEnd, Long appointmentId);

    boolean existsByPatient_IdAndDateAndTimeStartLessThanEqualAndTimeEndGreaterThanEqualAndIdNot(
            Long id, LocalDate date, LocalTime timeStart, LocalTime timeEnd, Long appointmentId);

}