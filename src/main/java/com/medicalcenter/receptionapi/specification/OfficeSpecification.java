package com.medicalcenter.receptionapi.specification;

import com.medicalcenter.receptionapi.domain.Office;
import org.springframework.data.jpa.domain.Specification;

public class OfficeSpecification {

  public static Specification<Office> withName(String name) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.like(root.get("name"), "%" + name + "%");
  }

  public static Specification<Office> withNumber(Integer number) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.like(root.get("number").as(String.class), "%" + number + "%");
  }
}
