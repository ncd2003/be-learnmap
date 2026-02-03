package com.learnmap.be.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqFeatureDto {
    @NotBlank(message = "Tính năng không được trống")
    private Long planFeatureId;
    private String description;
}
