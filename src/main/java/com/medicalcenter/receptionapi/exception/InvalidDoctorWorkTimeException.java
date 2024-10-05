package com.medicalcenter.receptionapi.exception;

public class InvalidDoctorWorkTimeException extends RuntimeException {

  public InvalidDoctorWorkTimeException() {
    super(
        "The end of a doctor's working hours cannot be "
            + "less than the beginning of his working hours");
  }
}
