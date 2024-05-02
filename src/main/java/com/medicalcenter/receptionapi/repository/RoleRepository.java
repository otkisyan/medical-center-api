package com.medicalcenter.receptionapi.repository;

import com.medicalcenter.receptionapi.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}