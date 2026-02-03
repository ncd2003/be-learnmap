package com.learnmap.be.domain.repositories;

import com.learnmap.be.domain.entities.Lesson;
import com.learnmap.be.domain.entities.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    Optional<Resource> findResourceById(Long id);
}
