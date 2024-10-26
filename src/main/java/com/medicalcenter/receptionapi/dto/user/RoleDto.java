package com.medicalcenter.receptionapi.dto.user;

import com.medicalcenter.receptionapi.domain.Role;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDto implements Serializable {

  private Long id;
  private String name;

  public static RoleDto ofEntity(Role role) {
    return RoleDto.builder().id(role.getId()).name(role.getName()).build();
  }
}
