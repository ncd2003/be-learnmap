package com.learnmap.be.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReqGeminiDto {
    private List<Content> contents;

    @Data
    public static class Content {
        private List<Part> parts;
    }

    @Data
    public static class Part {
        private String text;
    }
}