package com.learnmap.be.domain.dto;


import lombok.Data;

import java.util.List;

@Data
public class ResGeminiDto {
    private List<Candidate> candidates;

    @Data
    public static class Candidate {
        private Content content;
    }

    @Data
    public static class Content {
        private List<Part> parts;
    }

    @Data
    public static class Part {
        private String text;
    }
}

