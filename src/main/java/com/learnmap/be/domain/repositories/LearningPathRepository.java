package com.learnmap.be.domain.repositories;

import com.learnmap.be.domain.entities.Course;
import com.learnmap.be.domain.entities.LearningPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearningPathRepository extends JpaRepository<LearningPath,Long> {
    Boolean existsByCourse_IdAndPositionAndActiveTrue(Long courseId, Integer position);
}
