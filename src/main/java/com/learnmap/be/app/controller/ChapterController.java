package com.learnmap.be.app.controller;

import com.learnmap.be.domain.dto.ReqChapterDto;
import com.learnmap.be.domain.entities.Chapter;
import com.learnmap.be.domain.service.ChapterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @PostMapping
    public Chapter createChapter(@Valid @RequestBody ReqChapterDto reqChapterDto) {
        return chapterService.createChapter(reqChapterDto);
    }
}
