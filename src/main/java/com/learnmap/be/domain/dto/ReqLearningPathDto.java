package com.learnmap.be.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqLearningPathDto {
    @NotBlank(message = "Title can't blank")
    private String title;
    @NotNull(message = "Position can't blank")
    @Positive(message = "Position must be positive")
    private Integer position;
    @NotNull(message = "Course can't null")
    @Positive(message = "Course must be bigger than 0")
    private Long courseId;

}
