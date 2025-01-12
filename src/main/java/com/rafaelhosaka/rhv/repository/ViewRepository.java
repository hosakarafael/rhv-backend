package com.rafaelhosaka.rhv.repository;

import com.rafaelhosaka.rhv.models.View;
import com.rafaelhosaka.rhv.models.ViewId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewRepository extends JpaRepository<View, ViewId> {
}
