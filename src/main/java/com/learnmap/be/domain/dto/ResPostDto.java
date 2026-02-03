package com.learnmap.be.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResPostDto {
    private Long id;
    private String title; // nên có
    private String content;
    private String account;
}
