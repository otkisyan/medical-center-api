package com.medicalcenter.receptionapi.repository;

import com.medicalcenter.receptionapi.domain.RefreshSession;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshSessionRepository extends JpaRepository<RefreshSession, Long> {
  Optional<RefreshSession> findByToken(String token);

  RefreshSession findByUser_Username(String username);

  long deleteByToken(String token);

  boolean existsByToken(String token);
}
