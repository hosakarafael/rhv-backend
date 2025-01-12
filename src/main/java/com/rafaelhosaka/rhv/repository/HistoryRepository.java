package com.rafaelhosaka.rhv.repository;

import com.rafaelhosaka.rhv.models.History;
import com.rafaelhosaka.rhv.models.HistoryId;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, HistoryId> {
    List<History> findByUserId(Integer userId, Sort sort);
}
