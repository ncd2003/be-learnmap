package com.learnmap.be.domain.repositories;

import com.learnmap.be.domain.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic,Long> {
    List<Topic> findAllByActiveTrue();
    Optional<Topic> findTopicById(Long id);
}
