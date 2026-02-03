package com.learnmap.be.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqCourseDto {
    @NotBlank(message = "Title can't blank")
    private String title;
    @NotBlank(message = "Description can't blank")
    private String description;
    private String thumbnailUrl;
    @NotNull(message = "Price can't null")
    @Positive(message = "Price must be positive")
    private Double price;
    @NotNull(message = "Published can't blank")
    private Boolean published;
    private Long categoryId;
}
