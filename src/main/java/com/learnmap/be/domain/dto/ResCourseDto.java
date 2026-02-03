package com.learnmap.be.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResCourseDto {
    private Long id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private Double price;
    private Boolean published;
    private String category;
    private CourseLearningPath curLearningPath;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    static class CourseLearningPath{
        private List<ResLearningPathDto> learningPaths;
    }
}
