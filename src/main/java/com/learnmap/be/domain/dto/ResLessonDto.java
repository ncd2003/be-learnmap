package com.learnmap.be.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResLessonDto {
    private Long id;
    private String title;
    private Integer position;
    private Integer duration;
    private List<ResResourceDto> resources;
}
