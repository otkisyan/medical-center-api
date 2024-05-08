package com.medicalcenter.receptionapi.repository;


import com.medicalcenter.receptionapi.domain.RefreshSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshSessionRepository extends JpaRepository<RefreshSession, Long> {
    Optional<RefreshSession> findByToken(String token);

    RefreshSession findByUser_Username(String username);

    long deleteByToken(String token);

    boolean existsByToken(String token);
}