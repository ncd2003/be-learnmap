package com.learnmap.be.domain.service;

import com.learnmap.be.domain.dto.ReqPostDto;
import com.learnmap.be.domain.dto.ResPostDto;
import com.learnmap.be.domain.entities.Post;
import com.learnmap.be.domain.entities.Topic;
import com.learnmap.be.domain.exception.BadRequestException;
import com.learnmap.be.domain.utils.Constants;
import com.learnmap.be.domain.utils.JWTUtils;
import com.learnmap.be.domain.utils.MapperUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService extends BaseService {
    private final MapperUtils mapperUtils;

    public List<ResPostDto> getPostsByTopicId(Long topicId) {
        return postRepository.findAllByTopic_IdAndActiveTrue(topicId).stream().map(p -> mapperUtils.convertObjectToObject(p, ResPostDto.class)).toList();
    }

    public ResPostDto getPostById(Long postId) {
        Post postDB = postRepository.findPostById(postId).orElseThrow(() -> new BadRequestException(Constants.POST_NOT_FOUND));
        return mapperUtils.convertObjectToObject(postDB, ResPostDto.class);
    }

    public Post createPost(ReqPostDto reqPostDto) {
        Post post = mapperUtils.convertObjectToObject(reqPostDto, Post.class);
        post.setId(null);
        Topic topicDB = topicRepository.findTopicById(reqPostDto.getTopicId()).orElseThrow(() -> new BadRequestException(Constants.POST_NOT_FOUND));
        post.setTopic(topicDB);

        String email = JWTUtils.getCurrentUserLogin().isPresent() ? JWTUtils.getCurrentUserLogin().get() : null;
        post.setAccount(email);
        postRepository.save(post);
        return post;
    }
}
