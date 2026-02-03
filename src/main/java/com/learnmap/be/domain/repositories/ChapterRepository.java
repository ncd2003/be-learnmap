package com.learnmap.be.domain.repositories;

import com.learnmap.be.domain.entities.Chapter;
import com.learnmap.be.domain.entities.LearningPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    Boolean existsByLearningPath_IdAndPositionAndActiveTrue(Long learningPathId,Integer position);
}
