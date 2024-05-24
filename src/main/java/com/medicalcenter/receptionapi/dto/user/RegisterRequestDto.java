package com.medicalcenter.receptionapi.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.medicalcenter.receptionapi.security.enums.RoleAuthority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestDto implements Serializable {
    @JsonProperty("userCredentials")
    UserCredentialsDto userCredentialsDto;
    RoleAuthority role;
}
