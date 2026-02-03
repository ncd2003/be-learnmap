package com.learnmap.be.domain.service;

import com.learnmap.be.domain.dto.ReqCategoryDto;
import com.learnmap.be.domain.dto.ResCategoryDto;
import com.learnmap.be.domain.entities.Category;
import com.learnmap.be.domain.exception.BadRequestException;
import com.learnmap.be.domain.utils.MapperUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService extends BaseService {

    private final MapperUtils mapperUtils;

    public List<ResCategoryDto> findAll() {
        return categoryRepository.findAllByActiveTrue().stream().map(c -> mapperUtils.convertObjectToObject(c, ResCategoryDto.class)).toList();
    }

    public Category createCategory(ReqCategoryDto reqCategoryDto) {
        Category category = mapperUtils.convertObjectToObject(reqCategoryDto, Category.class);
        if (categoryRepository.findByName(reqCategoryDto.getName().trim()).isPresent()) {
            throw new BadRequestException("Category already exists");
        }
        categoryRepository.save(category);
        return category;
    }
}
