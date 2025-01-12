package com.rafaelhosaka.rhv.repository;

import com.rafaelhosaka.rhv.models.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Integer> {
    Optional<AuthUser> findByEmail(String email);
}
