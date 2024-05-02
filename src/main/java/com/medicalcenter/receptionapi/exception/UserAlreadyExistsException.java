package com.medicalcenter.receptionapi.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String msg){
        super(msg);
    }
}
