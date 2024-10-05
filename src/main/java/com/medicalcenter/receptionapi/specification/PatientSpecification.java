package com.medicalcenter.receptionapi.specification;

import com.medicalcenter.receptionapi.domain.Patient;
import java.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;

public class PatientSpecification {

  public static Specification<Patient> withName(String name) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), name + "%");
  }

  public static Specification<Patient> withSurname(String surname) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.like(root.get("surname"), surname + "%");
  }

  public static Specification<Patient> withMiddleName(String middleName) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.like(root.get("middleName"), middleName + "%");
  }

  public static Specification<Patient> withBirthDate(LocalDate birthDate) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.equal(root.get("birthDate"), birthDate);
  }
}
