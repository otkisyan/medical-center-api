package com.medicalcenter.receptionapi.dto.receptionist;

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
    ReceptionistResponseDto receptionistResponseDto;
    UserCredentialsDto userCredentialsDto;
}
