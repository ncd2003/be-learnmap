package com.learnmap.be.domain.repositories;

import com.learnmap.be.domain.dto.ResPostDto;
import com.learnmap.be.domain.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByTopic_IdAndActiveTrue(Long id);

    Optional<Post> findPostById(Long id);
}
