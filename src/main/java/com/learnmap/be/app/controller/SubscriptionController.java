package com.learnmap.be.app.controller;

import com.learnmap.be.domain.dto.ReqSubscriptionDto;
import com.learnmap.be.domain.entities.Subscription;
import com.learnmap.be.domain.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/subscription")
public class SubscriptionController{
    @Autowired
    private SubscriptionService subscriptionService;

    @PreAuthorize("hasAuthority('STUDENT') or hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @PostMapping
    public Subscription createSubscription(@Valid @RequestBody ReqSubscriptionDto reqSubscriptionDto) {
        return subscriptionService.createSubscription(reqSubscriptionDto);
    }
}
