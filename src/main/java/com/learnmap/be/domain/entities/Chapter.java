package com.learnmap.be.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "chapters")
public class Chapter extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;      // Hàm số
    private Integer position;  // Thứ tự

    @ManyToOne
    @JoinColumn(name = "learning_path_id")
    private LearningPath learningPath;

    @OneToMany(mappedBy = "chapter")
    @JsonIgnore
    private Set<Lesson> lessons = new HashSet<>();
}
