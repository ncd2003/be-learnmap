package com.learnmap.be.app.controller;

import com.learnmap.be.domain.dto.ReqPlanDto;
import com.learnmap.be.domain.dto.ResPlanDto;
import com.learnmap.be.domain.entities.Plan;
import com.learnmap.be.domain.service.PlanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/plan")
public class PlanController {
    @Autowired
    PlanService planService;

    @GetMapping("/public")
    public List<ResPlanDto> getAllPublicPlans() {
        return planService.getAllPublicPlans();
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @GetMapping
    public List<ResPlanDto> getAllPlans() {
        return planService.getAllPlans();
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @PostMapping
    public Plan createPlan(@Valid @RequestBody ReqPlanDto reqPlanDto) {
        return planService.createPlan(reqPlanDto);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @PutMapping("/{id}")
    public Plan updatePlan(@Valid @RequestBody ReqPlanDto reqPlanDto, @PathVariable Long id) {
        return planService.updatePlan(reqPlanDto,id);
    }
}
