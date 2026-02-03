package com.learnmap.be.app.controller;

import com.learnmap.be.domain.dto.ReqCategoryDto;
import com.learnmap.be.domain.dto.ResCategoryDto;
import com.learnmap.be.domain.entities.Category;
import com.learnmap.be.domain.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/public")
    public List<ResCategoryDto> getPublicCategories() {
        return categoryService.findAll();
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @PostMapping
    public Category createCategory(@Valid @RequestBody ReqCategoryDto reqCategoryDto) {
        return categoryService.createCategory(reqCategoryDto);
    }
}
