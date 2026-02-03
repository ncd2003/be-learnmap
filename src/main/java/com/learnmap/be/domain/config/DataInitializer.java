package com.learnmap.be.domain.config;

import com.learnmap.be.domain.service.FeatureService;
import com.learnmap.be.domain.service.PlanService;
import com.learnmap.be.domain.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final RoleService roleService;
    private final FeatureService featureService;
    private final PlanService planService;

    @Override
    public void run(String... args) throws Exception {
        if (roleService.getAllRoles().isEmpty()) {
            roleService.createDefaultRoles();
        }
        if (featureService.getAllFeatures().isEmpty()) {
            featureService.createDefaultFeature();
        }
        if (planService.getAllPlans().isEmpty()) {
            planService.createDefaultPlan();
        }
    }
}
