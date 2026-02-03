package com.learnmap.be.domain.service;

import com.learnmap.be.domain.dto.ReqFeatureDto;
import com.learnmap.be.domain.dto.ResFeatureDto;
import com.learnmap.be.domain.entities.Feature;
import com.learnmap.be.domain.utils.MapperUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FeatureService extends BaseService {
    private final MapperUtils mapperUtils;

    public List<ResFeatureDto> getAllFeatures() {
        return featureRepository.findFeatureByActiveTrue().stream().map(pf -> mapperUtils.convertObjectToObject(pf, ResFeatureDto.class)).toList();
    }

//    public List<ResFeatureDto> getAllById(Long Id) {
//        return featureRepository.findAllBy_IdAndActiveTrue(Id).stream().map(pf -> mapperUtils.convertObjectToObject(pf, ResFeatureDto.class)).toList();
//    }

    public Feature createFeature(ReqFeatureDto reqFeatureDto) {
        Feature feature = mapperUtils.convertObjectToObject(reqFeatureDto, Feature.class);
        feature.setId(null);
        return featureRepository.save(feature);
    }

    public void createDefaultFeature() {
        // 1️⃣ SEARCH
        Feature search = new Feature();
        search.setFeatureKey("SEARCH_ADMISSION_INFO");
        search.setDescription("Tìm kiếm thông tin tuyển sinh, điểm chuẩn các trường");
        featureRepository.save(search);

        // 2️⃣ BASIC TEST
        Feature basicTest = new Feature();
        basicTest.setFeatureKey("BASIC_ABILITY_TEST");
        basicTest.setDescription("Test năng lực cơ bản");
        featureRepository.save(basicTest);

        // 3️⃣ BASIC MATERIAL
        Feature basicMaterial = new Feature();
        basicMaterial.setFeatureKey("BASIC_STUDY_MATERIAL");
        basicMaterial.setDescription("Tài liệu học tập và video bài giảng cơ bản");
        featureRepository.save(basicMaterial);

        // 4️⃣ ADVANCED TEST
        Feature advancedTest = new Feature();
        advancedTest.setFeatureKey("ADVANCED_ABILITY_TEST");
        advancedTest.setDescription(
                "Trắc nghiệm chuyên sâu, báo cáo phân tích chi tiết lựa chọn ngành phù hợp"
        );
        featureRepository.save(advancedTest);

        // 5️⃣ ROADMAP
        Feature roadmap = new Feature();
        roadmap.setFeatureKey("STUDY_ROADMAP");
        roadmap.setDescription("Đề xuất lộ trình học tập cá nhân hóa");
        featureRepository.save(roadmap);

        // 6️⃣ FULL MATERIAL
        Feature fullMaterial = new Feature();
        fullMaterial.setFeatureKey("FULL_STUDY_MATERIAL");
        fullMaterial.setDescription("Toàn bộ tài liệu học tập và video bài giảng");
        featureRepository.save(fullMaterial);

        // 7️⃣ EXAM COURSE
        Feature examCourse = new Feature();
        examCourse.setFeatureKey("EXAM_PREPARATION_COURSE");
        examCourse.setDescription(
                "Khóa học luyện thi, theo dõi tiến trình, nhắc nhở, đánh giá và thi thử"
        );
        featureRepository.save(examCourse);

        // 8️⃣ CONSULTING
        Feature consulting = new Feature();
        consulting.setFeatureKey("CAREER_CONSULTING_1_1");
        consulting.setDescription("Chuyên gia hướng nghiệp tư vấn 1-1");
        featureRepository.save(consulting);
    }
}
