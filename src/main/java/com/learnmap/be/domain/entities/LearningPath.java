package com.learnmap.be.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "learning_paths")
public class LearningPath extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;     // Lộ trình cơ bản, nâng cao
    private Integer position; // Thứ tự hiển thị

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "learningPath")
    @JsonIgnore
    private Set<Chapter> chapters = new HashSet<>();
}
