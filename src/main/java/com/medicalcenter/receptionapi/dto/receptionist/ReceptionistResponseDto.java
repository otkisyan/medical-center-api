package com.medicalcenter.receptionapi.dto.receptionist;

import com.medicalcenter.receptionapi.domain.Receptionist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceptionistResponseDto implements Serializable {

    private Long id;
    private String name;
    private String surname;
    private String middleName;
    private LocalDate birthDate;

    public static ReceptionistResponseDto ofEntity(Receptionist receptionist) {
        return ReceptionistResponseDto.builder()
                .id(receptionist.getId())
                .name(receptionist.getName())
                .surname(receptionist.getSurname())
                .middleName(receptionist.getMiddleName())
                .birthDate(receptionist.getBirthDate())
                .build();
    }
}
