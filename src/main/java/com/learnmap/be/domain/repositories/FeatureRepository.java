package com.learnmap.be.domain.repositories;

import com.learnmap.be.domain.entities.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {
    List<Feature> findFeatureByActiveTrue();

    @Query("""
                select f.featureKey
                from Plan p
                Join Feature f
                WHERE p.code = :code
            """)
    List<String> findFeatureKeysByPlanCode(@Param("code") String planCode);
}
