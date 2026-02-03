package com.learnmap.be.domain.repositories;

import com.learnmap.be.domain.entities.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
        List<Plan> getAllByActiveTrue();
    @Query("""
                SELECT DISTINCT p
                FROM Plan p
                LEFT JOIN FETCH p.features
            """)
    List<Plan> getAllPublicByActiveTrue();

    Optional<Plan> findPlanById(Long id);

    Optional<Plan> findPlanByCode(String code);
}
