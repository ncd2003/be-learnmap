package com.learnmap.be.domain.repositories;

import com.learnmap.be.domain.dto.ResCareerQuestionDto;
import com.learnmap.be.domain.entities.CareerQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareerQuestionRepository extends JpaRepository<CareerQuestion, Long> {
    List<CareerQuestion> findAllByActiveTrue();
}
