package com.rafaelhosaka.gateway.repository;

import com.rafaelhosaka.gateway.models.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AuthUser, Integer> {
    Optional<AuthUser> findByEmail(String email);
}
