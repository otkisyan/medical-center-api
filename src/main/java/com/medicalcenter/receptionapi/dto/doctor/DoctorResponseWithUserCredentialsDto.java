package com.medicalcenter.receptionapi.dto.doctor;

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
public class DoctorResponseWithUserCredentialsDto implements Serializable {

    @JsonProperty("doctor")
    private DoctorResponseDto doctorResponseDto;
    @JsonProperty("userCredentials")
    private UserCredentialsDto userCredentialsDto;
}
