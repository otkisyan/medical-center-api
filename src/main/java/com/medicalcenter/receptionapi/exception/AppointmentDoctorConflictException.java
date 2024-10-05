package com.medicalcenter.receptionapi.exception;

public class AppointmentDoctorConflictException extends RuntimeException {
  public AppointmentDoctorConflictException() {
    super("The doctor already has an appointment for a scheduled time");
  }
}
