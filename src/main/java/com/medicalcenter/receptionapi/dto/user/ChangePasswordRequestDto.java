package com.medicalcenter.receptionapi.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordRequestDto implements Serializable {

  @NotNull @NotBlank private String currentPassword;
  @NotNull @NotBlank private String newPassword;
  @NotNull @NotBlank private String confirmPassword;
}
