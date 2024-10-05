package com.medicalcenter.receptionapi.exception;

public class InvalidTokenException extends RuntimeException {
  public InvalidTokenException(String msg) {
    super(msg);
  }

  public InvalidTokenException() {
    super(
        "The provided JWT token is either expired, tampered with, or has an incorrect signature.");
  }
}
