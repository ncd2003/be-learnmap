package com.learnmap.be.domain.repositories;

import com.learnmap.be.domain.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost_IdAndActiveTrue(Long postId);
}
