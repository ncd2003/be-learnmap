package com.learnmap.be.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqCategoryDto {
    @NotBlank(message = "Name can't blank")
    private String name;
    @NotBlank(message = "Description can't blank")
    private String description;
}
