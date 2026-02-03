package com.learnmap.be.domain.repositories;

import com.learnmap.be.domain.entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Optional<Lesson> findLessonById(Long id);
    Boolean existsByChapter_IdAndPositionAndActiveTrue(Long chapterId, Integer position);
}
