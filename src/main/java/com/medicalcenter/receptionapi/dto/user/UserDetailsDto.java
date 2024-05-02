package com.medicalcenter.receptionapi.dto.user;

import com.medicalcenter.receptionapi.security.CustomUserDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsDto {
    private Long id;
    private String username;
    private List<String> roles;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public static UserDetailsDto ofUserDetails(UserDetails userDetails) {
        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
        List<String> roles = customUserDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return UserDetailsDto.builder()
                .id(customUserDetails.getId())
                .username(customUserDetails.getUsername())
                .roles(roles)
                .accountNonExpired(customUserDetails.isAccountNonExpired())
                .accountNonLocked(customUserDetails.isAccountNonLocked())
                .credentialsNonExpired(customUserDetails.isCredentialsNonExpired())
                .enabled(customUserDetails.isEnabled())
                .build();
    }
}
