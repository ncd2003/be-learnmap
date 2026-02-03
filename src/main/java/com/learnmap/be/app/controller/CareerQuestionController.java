package com.learnmap.be.app.controller;

import com.learnmap.be.domain.dto.ReqAnswerQuestionDto;
import com.learnmap.be.domain.dto.ReqCareerQuestionDto;
import com.learnmap.be.domain.dto.ResCareerQuestionDto;
import com.learnmap.be.domain.dto.ResCareerResultDto;
import com.learnmap.be.domain.service.CareerQuestionService;
import com.learnmap.be.domain.service.HuggingFaceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/career-question")
public class CareerQuestionController {
    @Autowired
    private CareerQuestionService careerQuestionService;
    
    @Autowired
    private HuggingFaceService huggingFaceService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @GetMapping
    public List<ResCareerQuestionDto> findAll() {
        return careerQuestionService.findAll();
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @PostMapping
    public void createCareerQuestion(@Valid @RequestBody List<ReqCareerQuestionDto> reqCareerQuestionDtos) {
        careerQuestionService.createCareerQuestion(reqCareerQuestionDtos);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF') or hasAuthority('STUDENT')")
    @PostMapping("/calculate")
    public ResCareerResultDto calculateCareerQuestion(@Valid @RequestBody ReqAnswerQuestionDto reqAnswerQuestionDto) {
        return careerQuestionService.calculateAnswer(reqAnswerQuestionDto);
    }
    
    @GetMapping("/test-ai")
    public String testAI(@RequestParam(defaultValue = "Hello, how are you?") String prompt) {
        return huggingFaceService.generateCareerSuggestion(prompt);
    }
}
