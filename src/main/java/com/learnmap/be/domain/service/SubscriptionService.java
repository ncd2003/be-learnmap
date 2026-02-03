package com.learnmap.be.domain.service;

import com.learnmap.be.domain.dto.ReqSubscriptionDto;
import com.learnmap.be.domain.entities.Account;
import com.learnmap.be.domain.entities.Plan;
import com.learnmap.be.domain.entities.Subscription;
import com.learnmap.be.domain.entities.type.SubscriptionStatus;
import com.learnmap.be.domain.exception.BadRequestException;
import com.learnmap.be.domain.utils.Constants;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SubscriptionService extends BaseService {

    public Subscription createSubscription(ReqSubscriptionDto reqSubscriptionDto) {
        Optional<Subscription> subscriptionDB = subscriptionRepository.findSubscriptionByAccount_IdAndSubscriptionStatus(reqSubscriptionDto.getAccountId(), SubscriptionStatus.ACTIVE);
        if (subscriptionDB.isPresent()) {
            subscriptionDB.get().setSubscriptionStatus(SubscriptionStatus.EXPIRED);
            subscriptionRepository.save(subscriptionDB.get());
        }
        Subscription subscription = new Subscription();
        Account accountDB = accountRepository.findByIdAndActiveTrue(reqSubscriptionDto.getAccountId()).orElseThrow(() -> new BadRequestException(Constants.ACCOUNT_NOT_FOUND));
        Plan planDB = planRepository.findPlanById(reqSubscriptionDto.getPlanId()).orElseThrow(() -> new BadRequestException(Constants.PLAN_NOT_FOUND));
        subscription.setAccount(accountDB);
        subscription.setPlan(planDB);
        subscription.setStartAt(LocalDateTime.now());
        subscription.setEndAt(LocalDateTime.now().plusDays(planDB.getDurationInDays()));
        subscriptionRepository.save(subscription);
        return subscription;
    }
}
