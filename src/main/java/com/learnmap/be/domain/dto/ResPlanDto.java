package com.learnmap.be.domain.dto;

import com.learnmap.be.domain.entities.Feature;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResPlanDto {
    private Long id;
    private String code;
    private String name;
    private String description;
    private Double price;
    private Integer durationInDays;
    private List<Feature> features;

}
