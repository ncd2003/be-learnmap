package com.learnmap.be.domain.service;

import com.learnmap.be.domain.dto.ReqLearningPathDto;
import com.learnmap.be.domain.entities.Course;
import com.learnmap.be.domain.entities.LearningPath;
import com.learnmap.be.domain.exception.BadRequestException;
import com.learnmap.be.domain.utils.Constants;
import com.learnmap.be.domain.utils.MapperUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LearningPathService extends BaseService {
    private final MapperUtils mapperUtils;


    public LearningPath createLearningPath(ReqLearningPathDto reqLearningPathDto) {
        LearningPath learningPath = mapperUtils.convertObjectToObject(reqLearningPathDto, LearningPath.class);
        learningPath.setId(null);
        if(learningPathRepository.existsByCourse_IdAndPositionAndActiveTrue(reqLearningPathDto.getCourseId(),  reqLearningPathDto.getPosition())) {
            throw new BadRequestException(Constants.POSITION_DUPLICATED);
        }
        Course courseDB = courseRepository.findCourseById(reqLearningPathDto.getCourseId()).orElseThrow(() -> new BadRequestException(Constants.COURSE_NOT_FOUND));
        learningPath.setCourse(courseDB);
        learningPathRepository.save(learningPath);
        return learningPath;
    }
}
