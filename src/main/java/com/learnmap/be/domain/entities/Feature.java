package com.learnmap.be.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "features")
public class Feature extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String featureKey;
    private String description;
    @ManyToMany(mappedBy = "features")
    @JsonIgnore
    private List<Plan> plans = new ArrayList<>();
}

