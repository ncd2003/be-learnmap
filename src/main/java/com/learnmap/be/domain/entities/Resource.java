package com.learnmap.be.domain.entities;

import com.learnmap.be.domain.entities.type.ResourceType;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "resources")
public class Resource extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // Video bài giảng
    private String url;         // link video (S3, Vimeo, Bunny…)
    private ResourceType type;        // VIDEO, PDF, SLIDE
    private Long size;          // bytes

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
}
