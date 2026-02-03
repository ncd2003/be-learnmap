package com.learnmap.be.app.controller;

import com.learnmap.be.domain.dto.ReqLessonDto;
import com.learnmap.be.domain.entities.Lesson;
import com.learnmap.be.domain.service.LessonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/lesson")
public class LessonController {
    @Autowired
    private LessonService lessonService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @PostMapping()
    public Lesson createLesson(@Valid @RequestBody ReqLessonDto reqLessonDto) {
        return lessonService.createLesson(reqLessonDto);
    }
}
