package com.medicalcenter.receptionapi.exception;

public class AppointmentPatientConflictException extends RuntimeException {
    public AppointmentPatientConflictException() {
        super("The patient already has an appointment for a scheduled time");
    }
}
