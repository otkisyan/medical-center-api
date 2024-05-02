package com.medicalcenter.receptionapi.exception;


public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("Could not find resource");
    }
}