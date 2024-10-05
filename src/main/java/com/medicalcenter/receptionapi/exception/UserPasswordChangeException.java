package com.medicalcenter.receptionapi.exception;

public class UserPasswordChangeException extends RuntimeException {

  public UserPasswordChangeException(String msg) {
    super(msg);
  }
}
