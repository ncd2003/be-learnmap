package com.learnmap.be.domain.repositories;

import com.learnmap.be.domain.entities.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAllByActiveTrue();
    List<Course> findAllByActiveTrueAndPublishedTrue();
    List<Course> findAllByCategory_IdAndActiveTrueAndPublishedTrue(Long categoryId);
    Optional<Course> findCourseById(Long id);

    @EntityGraph(attributePaths = {
            "learningPaths",
            "learningPaths.chapters",
            "learningPaths.chapters.lessons",
            "learningPaths.chapters.lessons.resources"
    })
    Optional<Course> findDetailById(Long id);
}
