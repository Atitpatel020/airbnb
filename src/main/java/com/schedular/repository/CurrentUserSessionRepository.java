package com.schedular.repository;

import com.schedular.entity.CurrentUserSession;
import com.schedular.entity.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrentUserSessionRepository extends JpaRepository<CurrentUserSession,Long> {
    CurrentUserSession findByJwtToken(String token);
   Optional<CurrentUserSession> findById(long userId);
}
