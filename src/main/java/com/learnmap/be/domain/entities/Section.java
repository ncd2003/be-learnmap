package com.learnmap.be.domain.entities;
import jakarta.persistence.*;
import lombok.*;
@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sections")
public class Section extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // Học thử miễn phí
    private Integer orderIndex;

    @ManyToOne
    private Course course;
}
