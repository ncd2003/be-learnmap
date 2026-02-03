package com.learnmap.be.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqPostDto {
    @NotBlank(message = "Tiêu đề không được để trống")
    private String title;
    @NotBlank(message = "Nội dung không được để trống")
    private String content;
    private Long topicId;
}
