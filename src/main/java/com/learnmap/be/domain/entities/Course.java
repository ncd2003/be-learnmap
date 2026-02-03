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
@Table(name = "courses")
public class Course extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;        // Toán 12 - Luyện thi THPT
    private String description;  // Mô tả khóa học
    private String thumbnailUrl;

    private Double price;       // 0 = free
    private Boolean published;   // Đã public hay chưa

    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private Set<LearningPath> learningPaths = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
