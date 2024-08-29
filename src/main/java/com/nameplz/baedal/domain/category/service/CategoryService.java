package com.nameplz.baedal.domain.category.service;

import com.nameplz.baedal.domain.category.domain.Category;
import com.nameplz.baedal.domain.category.dto.response.CategoryResponseDto;
import com.nameplz.baedal.domain.category.mapper.CategoryMapper;
import com.nameplz.baedal.domain.category.repository.CategoryRepository;
import com.nameplz.baedal.global.common.exception.GlobalException;
import com.nameplz.baedal.global.common.response.ResultCase;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    /**
     * 카테고리 추가
     */
    @Transactional
    public String addCategory(String name) {
        categoryRepository.findByName(name).ifPresent(error -> {
            throw new GlobalException(ResultCase.INVALID_INPUT);
        });

        Category category = Category.createCategory(name);
        categoryRepository.save(category);
        return category.getId().toString();
    }

    /**
     * 카테고리 업데이트
     */
    @Transactional
    public CategoryResponseDto updateCategory(UUID categoryId, String categoryName) {
        Category category = findCategoryById(categoryId);
        category.updateCategoryName(categoryName);
        return categoryMapper.categoryToResponseDto(category);

    }

    /**
     * 카테고리 삭제
     */
    @Transactional
    public String deleteCategory(UUID categoryId, String username) {
        Category category = findCategoryById(categoryId);
        category.deleteEntity(username);

        return category.getId().toString();
    }

    /**
     * 카테고리 목록 조회
     */
    public List<CategoryResponseDto> getCategoryList(String name) {
        List<Category> categoryList = categoryRepository.getCategoryList(name);
        return categoryMapper.listCategoryToResponseDto(categoryList);
    }

    // 카테고리 Id 찾는 함수
    private Category findCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId)
            .orElseThrow(() -> new GlobalException(ResultCase.CATEGORY_NOT_FOUND));
    }

}

