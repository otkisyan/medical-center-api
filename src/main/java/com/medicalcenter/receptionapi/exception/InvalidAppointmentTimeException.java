package com.medicalcenter.receptionapi.exception;

public class InvalidAppointmentTimeException extends RuntimeException {

    public InvalidAppointmentTimeException() {
        super("Appointment is outside the doctor's working hours");
    }
}
