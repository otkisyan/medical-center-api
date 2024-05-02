package com.medicalcenter.receptionapi.dto.office;

import com.medicalcenter.receptionapi.domain.Office;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class OfficeDto implements Serializable {
    private Long id;
    private Integer number;
    private String name;

    public static OfficeDto ofEntity(Office office) {
        if (office == null) {
            return null;
        }
        return OfficeDto.builder().id(office.getId()).name(office.getName()).number(office.getNumber())
                .build();
    }

    public static Office toEntity(OfficeDto officeDto) {
        return Office.builder().name(officeDto.getName()).number(officeDto.getNumber()).build();
    }
}
