package com.learnmap.be.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResHuggingFaceDto {
    
    @JsonProperty("generated_text")
    private String generatedText;
    
    // For array response format
    private List<GeneratedResponse> responses;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeneratedResponse {
        @JsonProperty("generated_text")
        private String generatedText;
    }
}
