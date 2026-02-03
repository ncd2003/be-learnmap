package com.learnmap.be.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "plans")
public class Plan extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code; // FREE, STANDARD, PREMIUM

    private String name; // Free, Standard, Premium
    private String description;

    private Double price; // 0, 199000, 499000 (theo tháng)
    private Integer durationInDays; // 30, 365,...

    @ManyToMany
    @JoinTable(
            name = "plan_features",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    private List<Feature> features = new ArrayList<>();
}
