package com.learnmap.be.domain.service;

import com.learnmap.be.domain.dto.ReqPlanDto;
import com.learnmap.be.domain.dto.ResFeatureDto;
import com.learnmap.be.domain.dto.ResPlanDto;
import com.learnmap.be.domain.entities.Plan;
import com.learnmap.be.domain.entities.Feature;
import com.learnmap.be.domain.exception.BadRequestException;
import com.learnmap.be.domain.utils.Constants;
import com.learnmap.be.domain.utils.MapperUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PlanService extends BaseService {
    private final MapperUtils mapperUtils;

    public List<ResPlanDto> getAllPublicPlans() {
        return planRepository.getAllPublicByActiveTrue().stream().map(p -> mapperUtils.convertObjectToObject(p, ResPlanDto.class)).toList();
    }

    public List<ResPlanDto> getAllPlans() {
        return planRepository.getAllByActiveTrue().stream().map(p -> toDto(p)).toList();
    }


    public Plan createPlan(ReqPlanDto reqPlanDto) {
        Plan plan = mapperUtils.convertObjectToObject(reqPlanDto, Plan.class);
        plan.setId(null);
        List<Feature> findAllByFeatureId = featureRepository.findAllById(reqPlanDto.getPlanFeatureIds());
        if (findAllByFeatureId.isEmpty()) {
            throw new BadRequestException("1 số tính năng không tìm thấy");
        }
        plan.setFeatures(findAllByFeatureId);
        planRepository.save(plan);
        return plan;
    }

    public Plan updatePlan(ReqPlanDto reqPlanDto, Long id) {
        Plan plan = planRepository.findPlanById(id).orElseThrow(() -> new BadRequestException(Constants.PLAN_NOT_FOUND));
        List<Feature> findAllByFeatureId = featureRepository.findAllById(reqPlanDto.getPlanFeatureIds());
        if (findAllByFeatureId.isEmpty()) {
            throw new BadRequestException("1 số tính năng không tìm thấy");
        }
        plan.getFeatures().clear();
        plan.getFeatures().addAll(findAllByFeatureId);
        planRepository.save(plan);
        return plan;
    }

    public void createDefaultPlan() {
        if (planRepository.findPlanByCode("FREE").isEmpty()) {
            Plan plan = new Plan();
            plan.setCode("FREE");
            plan.setName("Miễn phí");
            plan.setDescription("Đây là gói miễn phí của người dùng");
            plan.setPrice(0D);
            plan.setDurationInDays(100000);
            plan.setActive(true);
            planRepository.save(plan);
        }
    }

    private ResPlanDto toDto(Plan plan) {
        ResPlanDto resPlanDto = new ResPlanDto();
        resPlanDto.setId(plan.getId());
        resPlanDto.setCode(plan.getCode());
        resPlanDto.setName(plan.getName());
        resPlanDto.setDescription(plan.getDescription());
        resPlanDto.setPrice(plan.getPrice());
        resPlanDto.setDurationInDays(plan.getDurationInDays());
        resPlanDto.setFeatures(plan.getFeatures());
        return resPlanDto;
    }
}
