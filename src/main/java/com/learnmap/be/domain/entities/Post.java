package com.learnmap.be.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import jakarta.persistence.*;
@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posts")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title; // nên có

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne()
    @JoinColumn(name = "topic_id")
    @JsonIgnore
    private Topic topic;

    private String account;

}
