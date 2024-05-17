package com.medicalcenter.receptionapi.specification;


import com.medicalcenter.receptionapi.domain.Receptionist;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ReceptionistSpecification {

    public static Specification<Receptionist> withName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), name + "%");
    }

    public static Specification<Receptionist> withSurname(String surname) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("surname"), surname + "%");
    }

    public static Specification<Receptionist> withMiddleName(String middleName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("middleName"), middleName + "%");
    }

    public static Specification<Receptionist> withBirthDate(LocalDate birthDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("birthDate"), birthDate);
    }
}
