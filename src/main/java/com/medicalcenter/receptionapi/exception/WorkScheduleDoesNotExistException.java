package com.medicalcenter.receptionapi.exception;

public class WorkScheduleDoesNotExistException extends RuntimeException{
    public WorkScheduleDoesNotExistException() {
        super("The doctor does not have a work schedule for the day selected");
    }
}
