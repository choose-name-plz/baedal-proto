package com.nameplz.baedal.domain.category.service;

import com.nameplz.baedal.domain.category.domain.Category;
import com.nameplz.baedal.domain.category.dto.response.CategoryResponseDto;
import com.nameplz.baedal.domain.category.mapper.CategoryMapper;
import com.nameplz.baedal.domain.category.repository.CategoryRepository;
import com.nameplz.baedal.global.common.exception.GlobalException;
import com.nameplz.baedal.global.common.response.ResultCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public String addCategory(String name) {
        categoryRepository.findByName(name).ifPresent(error -> {
            log.error("이미 존재하는 카테고리입니다 : {}", name);
            throw new GlobalException(ResultCase.INVALID_INPUT);
        });

        Category category = Category.createCategory(name);
        categoryRepository.save(category);
        return category.getId().toString();
    }

    public List<CategoryResponseDto> getCategoryList(String name) {
        List<Category> categoryList = categoryRepository.getCategoryList(name);
        return categoryMapper.categoryToResponseDto(categoryList);
    }
}

