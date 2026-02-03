package com.learnmap.be.app.controller;

import com.learnmap.be.domain.dto.ReqFeatureDto;
import com.learnmap.be.domain.dto.ResFeatureDto;
import com.learnmap.be.domain.entities.Feature;
import com.learnmap.be.domain.service.FeatureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feature")
public class FeatureController {
    @Autowired
    private FeatureService featureService;


//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
//    @GetMapping("/planId/{planId}")
//    public List<ResFeatureDto> getAllFeatureByPlanId(@PathVariable Long planId) {
//        return featureService.getAllByPlanId(planId);
//    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @GetMapping
    public List<ResFeatureDto> getAllFeature() {
        return featureService.getAllFeatures();
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @PostMapping
    public Feature createPlanFeature(@Valid @RequestBody ReqFeatureDto reqFeatureDto) {
        return featureService.createFeature(reqFeatureDto);
    }
}
