package com.medicalcenter.receptionapi.dto.receptionist;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.medicalcenter.receptionapi.dto.user.UserCredentialsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceptionistResponseWithUserCredentialsDto implements Serializable {

    @JsonProperty("receptionist")
    ReceptionistResponseDto receptionistResponseDto;
    @JsonProperty("userCredentials")
    UserCredentialsDto userCredentialsDto;
}
