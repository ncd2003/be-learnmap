package com.learnmap.be.domain.entities;

import com.learnmap.be.domain.entities.type.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subscriptions")
public class Subscription extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Account account;

    @ManyToOne
    private Plan plan;

    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private SubscriptionStatus subscriptionStatus = SubscriptionStatus.ACTIVE;

}
