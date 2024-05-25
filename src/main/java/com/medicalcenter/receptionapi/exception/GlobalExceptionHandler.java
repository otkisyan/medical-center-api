package com.medicalcenter.receptionapi.exception;

import com.medicalcenter.receptionapi.dto.error.Violation;
import com.medicalcenter.receptionapi.dto.error.ErrorResponse;
import com.medicalcenter.receptionapi.dto.error.ValidationErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponse> buildErrorResponse(String message, HttpStatus status, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(message)
                .error(status.getReasonPhrase())
                .path(request.getRequestURI())
                .status(status.value())
                .timestamp(new Date())
                .build();
        return new ResponseEntity<>(errorResponse, status);
    }

    private ResponseEntity<ValidationErrorResponse> buildValidationErrorResponse(String message,
                                                                                 HttpStatus status,
                                                                                 MethodArgumentNotValidException ex,
                                                                                 HttpServletRequest request
    ) {
        List<Violation> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new Violation(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();
        return new ResponseEntity<>(ValidationErrorResponse.builder()
                .message(message)
                .error(status.getReasonPhrase())
                .status(status.value())
                .timestamp(new Date())
                .violations(fieldErrors)
                .path(request.getRequestURI())
                .build(), status);
    }

    private ResponseEntity<ValidationErrorResponse> buildValidationErrorResponse(String message,
                                                                                 HttpStatus status,
                                                                                 ConstraintViolationException ex,
                                                                                 HttpServletRequest request
    ) {
        List<Violation> constraintViolations = ex.getConstraintViolations()
                .stream()
                .map(constraintViolation ->
                        new Violation(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage()))
                .toList();
        return new ResponseEntity<>(ValidationErrorResponse.builder()
                .message(message)
                .error(status.getReasonPhrase())
                .status(status.value())
                .timestamp(new Date())
                .violations(constraintViolations)
                .path(request.getRequestURI())
                .build(), status);
    }

    private ResponseEntity<ValidationErrorResponse> buildValidationErrorResponse(String message,
                                                                                 HttpStatus status,
                                                                                 HandlerMethodValidationException ex,
                                                                                 HttpServletRequest request
    ) {
        List<Violation> violations = ex.getAllValidationResults()
                .stream()
                .flatMap(parameterValidationResult -> parameterValidationResult.getResolvableErrors().stream()
                        .map(messageSourceResolvable -> new Violation(
                                parameterValidationResult.getMethodParameter().getParameterName(),
                                messageSourceResolvable.getDefaultMessage()))
                )
                .collect(Collectors.toList());
        return new ResponseEntity<>(ValidationErrorResponse.builder()
                .message(message)
                .error(status.getReasonPhrase())
                .status(status.value())
                .timestamp(new Date())
                .violations(violations)
                .path(request.getRequestURI())
                .build(), status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                                         HttpServletRequest request) {
        return buildValidationErrorResponse("Validation error", HttpStatus.BAD_REQUEST, ex, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorResponse> handleConstraintViolationException(ConstraintViolationException ex,
                                                                                      HttpServletRequest request) {
        return buildValidationErrorResponse("Validation error", HttpStatus.BAD_REQUEST, ex, request);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ValidationErrorResponse> handleTenantException(HandlerMethodValidationException ex, HttpServletRequest request) {

        return buildValidationErrorResponse("Validation error", HttpStatus.BAD_REQUEST, ex, request);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex,
                                                                               HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(UserPasswordChangeException.class)
    public ResponseEntity<ErrorResponse> handleUserPasswordChangeException(UserPasswordChangeException ex,
                                                                               HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(AppointmentDoctorConflictException.class)
    public ResponseEntity<ErrorResponse> handleAppointmentDoctorConflictException(AppointmentDoctorConflictException ex,
                                                                                  HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(AppointmentPatientConflictException.class)
    public ResponseEntity<ErrorResponse> handleAppointmentPatientConflictException(AppointmentPatientConflictException ex,
                                                                                   HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(InvalidAppointmentTimeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAppointmentTimeException(InvalidAppointmentTimeException ex,
                                                                               HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(HttpServletRequest request) {
        return buildErrorResponse("Invalid username or password", HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex,
                                                                         HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(WorkScheduleDoesNotExistException.class)
    public ResponseEntity<ErrorResponse> handleWorkScheduleDoesNotExistException(WorkScheduleDoesNotExistException ex,
                                                                         HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex,
                                                                         HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(InvalidTokenTypeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTokenTypeException(InvalidTokenTypeException ex,
                                                                         HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex,
                                                                          HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRefreshTokenNotFoundException(RefreshTokenNotFoundException ex,
                                                                             HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException ex,
                                                                     HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex,
                                                                        HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }
}

