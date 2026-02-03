package com.learnmap.be.app.controller;

import com.learnmap.be.domain.dto.ReqCourseDto;
import com.learnmap.be.domain.dto.ResCourseDto;
import com.learnmap.be.domain.entities.Course;
import com.learnmap.be.domain.service.CourseService;
import com.learnmap.be.domain.utils.annotation.RequireFeature;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @GetMapping
    public List<ResCourseDto> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/public")
    public List<ResCourseDto> getPublicCourses() {
        return courseService.getAllPublishedCourses();
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF') or hasAuthority('STUDENT')")
    @GetMapping("/{id}")
    public ResCourseDto getCourseById(@PathVariable @Positive Long id) {
        return courseService.getCourseById(id);
    }

    @GetMapping("/public/category/{id}")
    public List<ResCourseDto> getCourseByCategoryId(@PathVariable @Positive Long id) {
        return courseService.getCoursesByCategoryId(id);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @PostMapping
    public Course createCourse(@Valid @RequestBody ReqCourseDto reqCourseDto) {
        return courseService.createCourse(reqCourseDto);
    }


}
