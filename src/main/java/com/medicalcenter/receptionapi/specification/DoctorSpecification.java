package com.medicalcenter.receptionapi.specification;

import com.medicalcenter.receptionapi.domain.Doctor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class DoctorSpecification {

    public static Specification<Doctor> withName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), name + "%");
    }

    public static Specification<Doctor> withSurname(String surname) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("surname"), surname + "%");
    }

    public static Specification<Doctor> withMiddleName(String middleName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("middleName"), middleName + "%");
    }

    public static Specification<Doctor> withMedicalSpeciality(String medicalSpecialty) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("medicalSpecialty"), medicalSpecialty + "%");
    }

    public static Specification<Doctor> withBirthDate(LocalDate birthDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("birthDate"), birthDate);
    }
}
