package com.rafaelhosaka.rhv.repository;

import com.rafaelhosaka.rhv.models.Like;
import com.rafaelhosaka.rhv.models.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, LikeId> {
}
