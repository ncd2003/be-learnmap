package com.learnmap.be.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResChapterDto {
    private Long id;
    private String title;
    private Integer position;
    private List<ResLessonDto> lessons;
}
