package com.medicalcenter.receptionapi.specification;

import com.medicalcenter.receptionapi.domain.Appointment;
import com.medicalcenter.receptionapi.domain.Doctor;
import com.medicalcenter.receptionapi.domain.Patient;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentSpecification {
    public static Specification<Appointment> withPatient(String fullName) {
        return (root, query, criteriaBuilder) -> {
            String[] parts = fullName.split("[\\s.]");
            Predicate predicate = criteriaBuilder.conjunction();
            Join<Appointment, Patient> patient = root.join("patient");
            if (parts.length >= 1 && !parts[0].isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(patient.get("surname"), parts[0] + "%"));
            }
            if (parts.length >= 2 && !parts[1].isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(patient.get("name"), parts[1] + "%"));
            }
            if (parts.length >= 3 && !parts[2].isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(patient.get("middleName"), parts[2] + "%"));
            }
            return predicate;
        };
    }

    public static Specification<Appointment> withDoctor(String fullName) {
        return (root, query, criteriaBuilder) -> {
            String[] parts = fullName.split("[\\s.]");
            Predicate predicate = criteriaBuilder.conjunction();
            Join<Appointment, Doctor> doctor = root.join("doctor");
            if (parts.length >= 1 && !parts[0].isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(doctor.get("surname"), parts[0] + "%"));
            }
            if (parts.length >= 2 && !parts[1].isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(doctor.get("name"), parts[1] + "%"));
            }
            if (parts.length >= 3 && !parts[2].isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(doctor.get("middleName"), parts[2] + "%"));
            }
            return predicate;
        };
    }

    public static Specification<Appointment> withDate(LocalDate date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("date"), date);
    }

    public static Specification<Appointment> withTimeStart(LocalTime timeStart) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("timeStart"), timeStart);
    }
}
