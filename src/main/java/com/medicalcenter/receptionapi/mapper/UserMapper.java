package com.medicalcenter.receptionapi.mapper;

import com.medicalcenter.receptionapi.domain.Role;
import com.medicalcenter.receptionapi.domain.User;
import com.medicalcenter.receptionapi.dto.user.UserDetailsDto;
import com.medicalcenter.receptionapi.security.CustomUserDetails;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    imports = {CustomUserDetails.class})
public interface UserMapper {

  @Mapping(target = "roles", source = "authorities", qualifiedByName = "authoritiesToStringList")
  @Mapping(target = "id", expression = "java(((CustomUserDetails) userDetails).getId())")
  UserDetailsDto userDetailsToUserDetailsDto(UserDetails userDetails);

  @Mapping(target = "roles", source = "roles", qualifiedByName = "rolesToStringList")
  UserDetailsDto userToUserDetailsDto(User user);

  @Named("rolesToStringList")
  default List<String> rolesToStringList(List<Role> roles) {
    return roles.stream().map(Role::getName).collect(Collectors.toList());
  }

  @Named("authoritiesToStringList")
  default List<String> authoritiesToStringList(Collection<? extends GrantedAuthority> authorities) {
    return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
  }
}
