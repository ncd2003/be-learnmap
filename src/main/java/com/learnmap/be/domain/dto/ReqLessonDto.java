package com.learnmap.be.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqLessonDto {
    @NotBlank(message = "Tiêu đề không được để trống")
    private String title;
    @NotNull(message = "Vị trí không được để trống")
    @Positive(message = "Vị trí phải là số dương lớn hơn 0")
    private Integer position;
    @NotNull(message = "Thời hạn không được để trống")
    @PositiveOrZero(message = "Thời hạn phải lớn hơn 0")
    private Integer duration;
    @NotNull(message = "Chương không được trống")
    @Positive(message = "Chương phải lớn hơn 0")
    private Long chapterId;
}
