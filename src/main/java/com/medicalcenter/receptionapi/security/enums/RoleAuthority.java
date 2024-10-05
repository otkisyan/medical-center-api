package com.medicalcenter.receptionapi.security.enums;

public enum RoleAuthority {
  ADMIN("ROLE_ADMIN"),
  RECEPTIONIST("ROLE_RECEPTIONIST"),
  DOCTOR("ROLE_DOCTOR");

  public final String authority;

  RoleAuthority(String authority) {
    this.authority = authority;
  }
}
