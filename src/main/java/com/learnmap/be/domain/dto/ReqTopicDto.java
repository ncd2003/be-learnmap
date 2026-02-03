package com.learnmap.be.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqTopicDto {
    @NotBlank(message = "Tiêu đề không được để trống")
    private String title;
    @NotBlank(message = "Miêu tả không được để trống")
    private String description;
    private Boolean published;
}
