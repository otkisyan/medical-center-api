package com.medicalcenter.receptionapi.exception;

public class RefreshTokenNotFoundException extends RuntimeException {

  public RefreshTokenNotFoundException(String msg) {
    super(msg);
  }

  public RefreshTokenNotFoundException() {
    super("The provided refresh token was not found");
  }
}
