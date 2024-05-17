package com.medicalcenter.receptionapi.dto.receptionist;

import com.medicalcenter.receptionapi.dto.user.UserCredentialsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceptionistResponseWithUserCredentialsDto {
    ReceptionistResponseDto receptionistResponseDto;
    UserCredentialsDto userCredentialsDto;
}
