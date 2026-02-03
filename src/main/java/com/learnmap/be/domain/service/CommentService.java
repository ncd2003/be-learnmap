package com.learnmap.be.domain.service;

import com.learnmap.be.domain.dto.ReqCommentDto;
import com.learnmap.be.domain.dto.ResCommentDto;
import com.learnmap.be.domain.entities.Account;
import com.learnmap.be.domain.entities.Comment;
import com.learnmap.be.domain.entities.Post;
import com.learnmap.be.domain.exception.BadRequestException;
import com.learnmap.be.domain.utils.Constants;
import com.learnmap.be.domain.utils.JWTUtils;
import com.learnmap.be.domain.utils.MapperUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService extends BaseService {
    private final MapperUtils mapperUtils;

    public List<ResCommentDto> getCommentsByPostId(Long postId) {
        return commentRepository.findAllByPost_IdAndActiveTrue((postId)).stream().map(c -> mapperUtils.convertObjectToObject(c, ResCommentDto.class)).toList();
    }

    public Comment createComment(ReqCommentDto reqCommentDto) {
        Comment comment = mapperUtils.convertObjectToObject(reqCommentDto, Comment.class);
        comment.setId(null);
        Post postDB = postRepository.findById(reqCommentDto.getPostId()).orElseThrow(() -> new BadRequestException(Constants.POST_NOT_FOUND));
        comment.setPost(postDB);
        String email = JWTUtils.getCurrentUserLogin().isPresent() ? JWTUtils.getCurrentUserLogin().get() : null;
        if (email != null) {
            Account accountDB = accountRepository.findByEmailAndActiveTrue(email).orElseThrow(() -> new BadRequestException(Constants.ACCOUNT_NOT_FOUND));
            comment.setAccount(accountDB.getFullName());
        }
        commentRepository.save(comment);
        return comment;
    }
}
