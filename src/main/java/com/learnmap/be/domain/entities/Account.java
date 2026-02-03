package com.learnmap.be.domain.entities;

import com.learnmap.be.domain.entities.type.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accounts")
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String fullName;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(unique = true, length = 20)
    private String phoneNumber;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UserStatus status;

    @Column(name = "refresh_token", columnDefinition = "TEXT")
    private String refreshToken;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
