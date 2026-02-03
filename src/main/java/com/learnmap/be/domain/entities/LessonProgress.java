package com.learnmap.be.domain.entities;

import lombok.*;
import jakarta.persistence.*;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lesson_progress")
public class LessonProgress extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Account student;

    @ManyToOne
    private Lesson lesson;

    private Boolean completed;
    private Integer watchedSeconds;
}