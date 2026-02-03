package com.learnmap.be.domain.service;

import com.learnmap.be.domain.dto.CourseMapper;
import com.learnmap.be.domain.dto.ReqCourseDto;
import com.learnmap.be.domain.dto.ResCourseDto;
import com.learnmap.be.domain.entities.*;
import com.learnmap.be.domain.exception.BadRequestException;
import com.learnmap.be.domain.utils.Constants;
import com.learnmap.be.domain.utils.MapperUtils;
import com.learnmap.be.domain.utils.annotation.RequireFeature;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService extends BaseService {
    private final MapperUtils mapperUtils;

    @Value("${aws.s3.prefix-url}")
    private String prefixURL;

    public CourseService(MapperUtils mapperUtils) {
        this.mapperUtils = mapperUtils;
    }
    public List<ResCourseDto> getAllCourses() {
        return courseRepository.findAllByActiveTrue().stream().map(c -> CourseMapper.toDto(c)).toList();
    }

    public List<ResCourseDto> getAllPublishedCourses() {
        return courseRepository.findAllByActiveTrueAndPublishedTrue().stream().map(c -> mapperUtils.convertObjectToObject(c, ResCourseDto.class)).toList();
    }
    public List<ResCourseDto> getCoursesByCategoryId(Long categoryId) {
        return courseRepository.findAllByCategory_IdAndActiveTrueAndPublishedTrue(categoryId).stream().map(c -> mapperUtils.convertObjectToObject(c, ResCourseDto.class)).toList();
    }

    @Transactional
    public Course createCourse(ReqCourseDto reqCourseDto) {
        Course course = mapperUtils.convertObjectToObject(reqCourseDto, Course.class);
        course.setId(null);
        course.setThumbnailUrl(prefixURL+"/"+reqCourseDto.getThumbnailUrl());
        Category categoryDB = categoryRepository.findById(reqCourseDto.getCategoryId()).orElseThrow(() -> new BadRequestException(Constants.CATEGORY_NOT_FOUND));
        course.setCategory(categoryDB);
        courseRepository.save(course);
        return course;
    }

    @Transactional
    public ResCourseDto getCourseById(Long id) {
        return CourseMapper.toDto(courseRepository.findDetailById(id).orElseThrow(() -> new BadRequestException(Constants.COURSE_NOT_FOUND)));
    }

}

