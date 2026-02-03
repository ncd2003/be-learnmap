package com.learnmap.be.domain.entities;

import lombok.*;
import jakarta.persistence.*;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "enrollments")
public class Enrollment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Account student;

    @ManyToOne
    private Course course;

    private Boolean paid; // Đã thanh toán hay chưa
}
