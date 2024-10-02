package com.medicalcenter.receptionapi.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseCookie;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponeWithResponseCookieDto {
    AuthResponseDto authResponseDto;
    ResponseCookie responseCookie;
}
