package com.learnmap.be.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqHuggingFaceDto {
    private String inputs;
    private Parameters parameters;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Parameters {
        private Integer max_new_tokens = 2000;
        private Double temperature = 0.7;
        private Double top_p = 0.95;
        private Boolean return_full_text = false;
    }
}
