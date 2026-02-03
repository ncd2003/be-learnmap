package com.learnmap.be.app.controller;

import com.learnmap.be.domain.dto.ReqLearningPathDto;
import com.learnmap.be.domain.dto.ResLearningPathDto;
import com.learnmap.be.domain.entities.LearningPath;
import com.learnmap.be.domain.service.LearningPathService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/learning-path")
public class LearningPathController {

    @Autowired
    private LearningPathService learningPathService;

    //    @GetMapping
//    public List<ResLearningPathDto> getLearningPathByCourseId()
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @PostMapping
    public LearningPath createLearningPath(@Valid @RequestBody ReqLearningPathDto reqLearningPathDto) {
        return learningPathService.createLearningPath(reqLearningPathDto);
    }
}
