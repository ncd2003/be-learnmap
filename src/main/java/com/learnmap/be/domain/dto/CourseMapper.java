package com.learnmap.be.domain.dto;

import com.learnmap.be.domain.entities.*;

public class CourseMapper {
    public static ResCourseDto toDto(Course course) {
        ResCourseDto dto = new ResCourseDto();
        ResCourseDto.CourseLearningPath courseLearningPath = new ResCourseDto.CourseLearningPath();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setThumbnailUrl(course.getThumbnailUrl());
        dto.setPrice(course.getPrice());
        dto.setPublished(course.getPublished());
        dto.setCategory(course.getCategory().getName());
        if (!course.getLearningPaths().isEmpty()) {
            courseLearningPath.setLearningPaths(
                    course.getLearningPaths().stream()
                            .map(CourseMapper::toDto)
                            .toList()
            );
        }
        dto.setCurLearningPath(courseLearningPath);
        return dto;
    }

    private static ResLearningPathDto toDto(LearningPath lp) {
        ResLearningPathDto dto = new ResLearningPathDto();
        dto.setId(lp.getId());
        dto.setTitle(lp.getTitle());
        dto.setPosition(lp.getPosition());
        dto.setChapters(
                lp.getChapters().stream()
                        .map(CourseMapper::toDto)
                        .toList()
        );

        return dto;
    }

    private static ResChapterDto toDto(Chapter ch) {
        ResChapterDto dto = new ResChapterDto();
        dto.setId(ch.getId());
        dto.setTitle(ch.getTitle());
        dto.setPosition(ch.getPosition());
        dto.setLessons(
                ch.getLessons().stream()
                        .map(CourseMapper::toDto)
                        .toList()
        );

        return dto;
    }

    private static ResLessonDto toDto(Lesson l) {
        ResLessonDto dto = new ResLessonDto();
        dto.setId(l.getId());
        dto.setTitle(l.getTitle());
        dto.setDuration(l.getDuration());
        dto.setPosition(l.getPosition());
        dto.setResources(
                l.getResources().stream()
                        .map(r -> new ResResourceDto(r.getId(), r.getName(), r.getUrl(), r.getType(), r.getSize()))
                        .toList()
        );

        return dto;
    }

}
