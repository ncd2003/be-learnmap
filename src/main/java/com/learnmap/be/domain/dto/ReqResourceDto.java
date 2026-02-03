package com.learnmap.be.domain.dto;

import com.learnmap.be.domain.entities.type.ResourceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqResourceDto {
    @NotBlank(message = "Name can't blank")
    private String name;
    private String url;
    private ResourceType type;
    private Long size;
    @NotNull(message = "Lesson can't null")
    @Positive(message = "Lesson must be bigger than 0")
    private Long lessonId;
}
