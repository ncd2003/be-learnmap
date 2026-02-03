package com.learnmap.be.app.controller;

import com.learnmap.be.domain.dto.ReqTopicDto;
import com.learnmap.be.domain.dto.ResTopicDto;
import com.learnmap.be.domain.entities.Topic;
import com.learnmap.be.domain.service.TopicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/topic")
public class TopicController {
    @Autowired
    private TopicService topicService;


    @GetMapping("/public")
    public List<ResTopicDto> getAll() {
        return topicService.getTopics();
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @PostMapping
    public Topic createTopic(@Valid @RequestBody ReqTopicDto reqTopicDto) {
        return topicService.createTopic(reqTopicDto);
    }
}
