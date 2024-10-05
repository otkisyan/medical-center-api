package com.medicalcenter.receptionapi.dto.error;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse implements Serializable {
  private String message;
  private String path;
  private String error;
  private int status;
  private Date timestamp;
}
