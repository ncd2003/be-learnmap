package com.learnmap.be.app.controller;

import com.learnmap.be.domain.dto.ReqPostDto;
import com.learnmap.be.domain.dto.ResPostDto;
import com.learnmap.be.domain.entities.Post;
import com.learnmap.be.domain.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/public/{topicId}")
    public List<ResPostDto> getPostsByTopicId(@PathVariable Long topicId) {
        return postService.getPostsByTopicId(topicId);
    }

    @GetMapping("/post/{postId}")
    public ResPostDto getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF') or hasAuthority('STUDENT')" )
    @PostMapping
    public Post createPost(@Valid @RequestBody ReqPostDto reqPostDto) {
        return postService.createPost(reqPostDto);
    }
}
