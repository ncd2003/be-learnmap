package com.learnmap.be.app.controller;

import com.learnmap.be.domain.dto.ReqCommentDto;
import com.learnmap.be.domain.dto.ResCommentDto;
import com.learnmap.be.domain.entities.Comment;
import com.learnmap.be.domain.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @GetMapping("/public/{postId}")
    public List<ResCommentDto> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId);
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF') or hasAuthority('STUDENT')")
    @PostMapping
    public Comment createComment(@RequestBody ReqCommentDto reqCommentDto) {
        return commentService.createComment(reqCommentDto);
    }
}
