package com.learnmap.be.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqChapterDto {
    @NotBlank(message = "Title can't blank")
    private String title;
    @NotNull(message = "Position can't blank")
    @Positive(message = "Position must be positive")
    private Integer position;
    @NotNull(message = "LearningPath can't blank")
    @Positive(message = "LearningPath must be bigger than 0")
    private Long learningPathId;
}
