package com.learnmap.be.domain.repositories;

import com.learnmap.be.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByActiveTrue();
    Optional<Category> findByName(String name);
}
