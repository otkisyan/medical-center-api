package com.medicalcenter.receptionapi.dto.error;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class ValidationErrorResponse implements Serializable {
    private String message;
    private String path;
    private int status;
    private LocalDateTime timestamp;
    List<BindingError> fieldErrors;
}
