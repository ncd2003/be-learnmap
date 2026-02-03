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
@Table(name = "lessons")
public class Lesson extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;      // Đồng biến – nghịch biến
    private Integer position;

    private Integer duration; // Thời lượng (giây)

    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    @OneToMany(mappedBy = "lesson")
    @JsonIgnore
    private Set<Resource> resources = new HashSet<>();
}

