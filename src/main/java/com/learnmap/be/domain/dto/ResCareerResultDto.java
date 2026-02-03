package com.learnmap.be.domain.dto;

import com.learnmap.be.domain.entities.type.CareerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ResCareerResultDto {
    private String riasec;
    private Map<CareerType, Integer> scores;
    private String suggestion;
}
