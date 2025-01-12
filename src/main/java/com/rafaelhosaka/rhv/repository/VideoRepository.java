package com.rafaelhosaka.rhv.repository;

import com.rafaelhosaka.rhv.models.Video;
import com.rafaelhosaka.rhv.models.Visibility;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Integer> {
    List<Video> findByVisibility(Visibility visibility, Sort sort);
    Optional<Video> findByIdAndVisibility(Integer id, Visibility visibility);
    List<Video> findAllByUserIdInAndVisibility(List<Integer> ids, Visibility visibility , Sort sort);
    List<Video> findAllByUserId(Integer id, Sort sort);
    List<Video> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String titleQuery, String descriptionQuery);
}
