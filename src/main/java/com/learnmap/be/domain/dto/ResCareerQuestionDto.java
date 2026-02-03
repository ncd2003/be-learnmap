package com.learnmap.be.domain.dto;

import com.learnmap.be.domain.entities.type.CareerType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResCareerQuestionDto {
    private Long id;
    private String content;
    private CareerType careerType;
}
