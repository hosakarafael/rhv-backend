package com.rafaelhosaka.rhv.repository;

import com.rafaelhosaka.rhv.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
