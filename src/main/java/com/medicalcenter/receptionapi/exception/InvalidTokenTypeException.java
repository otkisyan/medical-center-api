package com.medicalcenter.receptionapi.exception;

public class InvalidTokenTypeException extends RuntimeException {

    public InvalidTokenTypeException(String msg) {
        super(msg);
    }

    public InvalidTokenTypeException() {
        super("Provided token has invalid 'type' claim or doesn't have 'type' claim");
    }
}
