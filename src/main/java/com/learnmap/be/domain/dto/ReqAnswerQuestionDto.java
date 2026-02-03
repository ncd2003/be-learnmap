package com.learnmap.be.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ReqAnswerQuestionDto {
    Map<Long, Integer> answers;
}
