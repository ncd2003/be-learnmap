package com.learnmap.be.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReqPlanDto {
    private String code;
    private String name;
    private String description;
    private Double price;
    private Integer durationInDays;
    @NotNull
    private List<Long> planFeatureIds;
}
