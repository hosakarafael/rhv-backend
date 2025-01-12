package com.rafaelhosaka.rhv.repository;

import com.rafaelhosaka.rhv.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    @Query(value = "SELECT COUNT(*) FROM subscriptions WHERE creator_id = :creatorId", nativeQuery = true)
    int countSubscribers(@Param("creatorId") Integer creatorId);
}
