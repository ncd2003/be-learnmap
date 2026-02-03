package com.learnmap.be.domain.service;

import com.learnmap.be.domain.dto.ReqAnswerQuestionDto;
import com.learnmap.be.domain.dto.ReqCareerQuestionDto;
import com.learnmap.be.domain.dto.ResCareerQuestionDto;
import com.learnmap.be.domain.dto.ResCareerResultDto;
import com.learnmap.be.domain.entities.CareerQuestion;
import com.learnmap.be.domain.entities.type.CareerType;
import com.learnmap.be.domain.exception.BadRequestException;
import com.learnmap.be.domain.utils.MapperUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CareerQuestionService extends BaseService {

    private final MapperUtils mapperUtils;
    private final HuggingFaceService huggingFaceService;

    public List<ResCareerQuestionDto> findAll() {
        return careerQuestionRepository.findAllByActiveTrue().stream().map(cq -> mapperUtils.convertObjectToObject(cq, ResCareerQuestionDto.class)).toList();
    }

    @Transactional
    public void createCareerQuestion(List<ReqCareerQuestionDto> reqCareerQuestionDtos) {
        reqCareerQuestionDtos.forEach(reqCareerQuestionDto -> {
            CareerQuestion careerQuestion = mapperUtils.convertObjectToObject(reqCareerQuestionDto, CareerQuestion.class);
            careerQuestionRepository.save(careerQuestion);
        });
    }

    private Map<CareerType, Integer> calculateScores(ReqAnswerQuestionDto reqAnswerQuestionDto) {

        Map<CareerType, Integer> scores = new EnumMap<>(CareerType.class);

        // Khởi tạo điểm = 0 cho tất cả CareerType
        for (CareerType careerType : CareerType.values()) {
            scores.put(careerType, 0);
        }

        // Cộng điểm theo câu trả lời
        reqAnswerQuestionDto.getAnswers().forEach((questionId, score) -> {

            if (score < 1 || score > 5) {
                throw new BadRequestException("Điểm không hợp lệ (1–5)");
            }

            CareerQuestion careerQuestion = careerQuestionRepository
                    .findById(questionId)
                    .orElseThrow(() -> new BadRequestException("Không tìm thấy câu hỏi"));

            CareerType careerType = careerQuestion.getCareerType();
            scores.put(careerType, scores.get(careerType) + score);
        });

        return scores;
    }

    private String getTopCareerResult(Map<CareerType, Integer> scores) {

        return scores.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .limit(3)
                .map(entry -> entry.getKey().name())
                .collect(Collectors.joining());
    }

    public ResCareerResultDto calculateAnswer(ReqAnswerQuestionDto dto) {
        // 1. Tính điểm (rule-based)
        Map<CareerType, Integer> scores = calculateScores(dto);

        String riasec = getTopCareerResult(scores);

        String suggestion = huggingFaceService.analyze(riasec, scores);
        return new ResCareerResultDto(riasec, scores, suggestion);
    }
}
