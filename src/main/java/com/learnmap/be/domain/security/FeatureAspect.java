package com.learnmap.be.domain.security;

import com.learnmap.be.domain.exception.ForBiddenException;
import com.learnmap.be.domain.repositories.FeatureRepository;
import com.learnmap.be.domain.utils.annotation.RequireFeature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class FeatureAspect {
    @Autowired
    private FeatureRepository featureRepository;

    @Before("@annotation(requireFeature)")
    public void check(RequireFeature requireFeature) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String plan = jwt.getClaimAsString("plan");
        List<String> features = featureRepository.findFeatureKeysByPlanCode(plan);
        if (!features.contains(requireFeature.value())) {
            throw new ForBiddenException("Vui lòng nâng cấp gói để sử dụng tính năng");
        }
    }
}
