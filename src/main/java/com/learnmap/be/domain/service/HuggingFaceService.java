package com.learnmap.be.domain.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnmap.be.domain.dto.ReqHuggingFaceDto;
import com.learnmap.be.domain.dto.ResHuggingFaceDto;
import com.learnmap.be.domain.entities.type.CareerType;
import com.learnmap.be.domain.exception.HuggingFaceApiException;
import com.learnmap.be.domain.exception.HuggingFaceRateLimitException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class HuggingFaceService {
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    @Value("${huggingface.api.key}")
    private String apiKey;

    @Value("${huggingface.api.url}")
    private String apiUrl;

    @Value("${huggingface.api.max-retries:3}")
    private int maxRetries;

    @Value("${huggingface.api.initial-retry-delay:1000}")
    private long initialRetryDelay;

    public HuggingFaceService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public String generateCareerSuggestion(String prompt) {
        ReqHuggingFaceDto request = buildRequest(prompt);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<ReqHuggingFaceDto> entity = new HttpEntity<>(request, headers);

        return executeWithRetry(() -> {
            try {
                ResponseEntity<String> response = restTemplate.postForEntity(
                        apiUrl,
                        entity,
                        String.class
                );
                return extractText(response.getBody());
            } catch (HttpClientErrorException.TooManyRequests e) {
                log.warn("HuggingFace API rate limit exceeded: {}", e.getMessage());
                throw new HuggingFaceRateLimitException(
                        "Hệ thống đang quá tải. Vui lòng thử lại sau vài phút. " +
                        "Quota của HuggingFace API đã vượt giới hạn."
                );
            } catch (HttpClientErrorException e) {
                log.error("HuggingFace API client error: {} - {}", e.getStatusCode(), e.getMessage());
                throw new HuggingFaceApiException(
                        "Không thể kết nối tới dịch vụ HuggingFace API. Vui lòng thử lại sau."
                );
            } catch (RestClientException e) {
                log.error("HuggingFace API communication error: {}", e.getMessage(), e);
                throw new HuggingFaceApiException(
                        "Lỗi kết nối tới dịch vụ HuggingFace API. Vui lòng kiểm tra kết nối mạng."
                );
            } catch (Exception e) {
                log.error("Unexpected error calling HuggingFace API: {}", e.getMessage(), e);
                throw new HuggingFaceApiException(
                        "Lỗi không xác định khi gọi HuggingFace API."
                );
            }
        });
    }

    private <T> T executeWithRetry(RetryableOperation<T> operation) {
        int attempt = 0;
        long delay = initialRetryDelay;

        while (attempt < maxRetries) {
            try {
                return operation.execute();
            } catch (HuggingFaceRateLimitException e) {
                attempt++;
                if (attempt >= maxRetries) {
                    log.error("Max retry attempts ({}) reached for HuggingFace API", maxRetries);
                    throw e;
                }
                
                log.warn("Rate limit hit, retry attempt {}/{} after {}ms", 
                        attempt, maxRetries, delay);
                
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new HuggingFaceApiException("Request interrupted during retry");
                }
                
                // Exponential backoff: double the delay for next retry
                delay *= 2;
            }
        }

        throw new HuggingFaceApiException("Failed after " + maxRetries + " retry attempts");
    }

    @FunctionalInterface
    private interface RetryableOperation<T> {
        T execute();
    }

    private ReqHuggingFaceDto buildRequest(String prompt) {
        ReqHuggingFaceDto.Parameters parameters = new ReqHuggingFaceDto.Parameters();
        parameters.setMax_new_tokens(2000);
        parameters.setTemperature(0.7);
        parameters.setTop_p(0.95);
        parameters.setReturn_full_text(false);

        ReqHuggingFaceDto request = new ReqHuggingFaceDto();
        request.setInputs(prompt);
        request.setParameters(parameters);

        return request;
    }

    private String extractText(String responseBody) {
        try {
            // Try parsing as array first (most common format)
            List<ResHuggingFaceDto.GeneratedResponse> responses = 
                objectMapper.readValue(responseBody, new TypeReference<List<ResHuggingFaceDto.GeneratedResponse>>() {});
            
            if (responses != null && !responses.isEmpty()) {
                return responses.get(0).getGeneratedText();
            }
        } catch (Exception e) {
            log.debug("Response is not an array format, trying object format");
        }

        try {
            // Try parsing as single object
            ResHuggingFaceDto response = objectMapper.readValue(responseBody, ResHuggingFaceDto.class);
            if (response != null && response.getGeneratedText() != null) {
                return response.getGeneratedText();
            }
        } catch (Exception e) {
            log.error("Failed to parse HuggingFace response: {}", e.getMessage());
        }

        return "Không có gợi ý phù hợp.";
    }

    public String analyze(String riasec, Map<CareerType, Integer> scores) {
        String prompt = """
                Tôi có kết quả trắc nghiệm hướng nghiệp theo mô hình RIASEC.
                
                Kết quả:
                - Mã RIASEC: %s
                - Điểm chi tiết: %s
                
                Hãy:
                1. Phân tích tính cách nghề nghiệp.
                2. Gợi ý 5 nghề phù hợp.
                3. Gợi ý 5 ngành học phù hợp.
                
                Viết bằng tiếng Việt, dễ hiểu cho học sinh.
                """.formatted(riasec, scores);

        return generateCareerSuggestion(prompt);
    }
}
