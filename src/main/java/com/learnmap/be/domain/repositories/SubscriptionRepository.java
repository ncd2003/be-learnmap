package com.learnmap.be.domain.repositories;

import com.learnmap.be.domain.entities.Subscription;
import com.learnmap.be.domain.entities.type.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription>findSubscriptionByAccount_IdAndSubscriptionStatus(Long accountId, SubscriptionStatus subscriptionStatus);
    Optional<Subscription>findSubscriptionByAccount_Id(Long accountId);
}
