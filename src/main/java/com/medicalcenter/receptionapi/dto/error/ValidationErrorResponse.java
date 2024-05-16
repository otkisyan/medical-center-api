package com.medicalcenter.receptionapi.dto.error;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidationErrorResponse {
    private String message;
    private String path;
    private String error;
    private int status;
    private Date timestamp;
    List<Violation> violations;
}
