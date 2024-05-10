package com.medicalcenter.receptionapi.dto.office;

import com.medicalcenter.receptionapi.domain.Office;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfficeRequestDto {

    private Integer number;
    private String name;

    public static Office toEntity(OfficeRequestDto officeRequestDto) {
        return Office.builder().name(officeRequestDto.getName()).number(officeRequestDto.getNumber()).build();
    }
}
