package com.nameplz.baedal.domain.category.controller;

import com.nameplz.baedal.domain.category.dto.request.CategoryAddRequestDto;
import com.nameplz.baedal.domain.category.dto.request.CategoryUpdateRequestDto;
import com.nameplz.baedal.domain.category.dto.response.CategoryIdResponseDto;
import com.nameplz.baedal.domain.category.dto.response.CategoryResponseDto;
import com.nameplz.baedal.domain.category.service.CategoryService;
import com.nameplz.baedal.global.common.response.CommonResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("category")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    /*
        카테고리 생성
     */
    @PostMapping
    public ResponseEntity<CommonResponse<CategoryIdResponseDto>> addCategory(
        @RequestBody CategoryAddRequestDto requestBody) {
        //TODO: 마스터만 호출할 수 있도록 추가
        String categoryId = categoryService.addCategory(requestBody.name());
        return new ResponseEntity<>(
            CommonResponse.success(new CategoryIdResponseDto(categoryId)), HttpStatus.CREATED);
    }

    /*
        카테고리 목록 조회
     */
    @GetMapping
    public CommonResponse<List<CategoryResponseDto>> getCategoryByName(
        @RequestParam(value = "name", required = false) String name
    ) {
        List<CategoryResponseDto> category = categoryService.getCategoryList(name);
        return CommonResponse.success(category);
    }

    /*
        카테고리 수정
     */
    @PutMapping("/{id}")
    public CommonResponse<CategoryResponseDto> updateCategory(
        @PathVariable("id") UUID categoryId,
        @RequestBody CategoryUpdateRequestDto requestBody
    ) {

        //TODO: 마스터만 호출할 수 있도록 추가
        CategoryResponseDto categoryResponseDto = categoryService.updateCategory(categoryId,
            requestBody.categoryName());

        return CommonResponse.success(categoryResponseDto);
    }

    /*
        카테고리 삭제
     */
    @DeleteMapping("/{id}")
    public CommonResponse<CategoryIdResponseDto> deleteCategory(
        @PathVariable("id") UUID categoryId
    ) {
        //TODO: 마스터만 호출할 수 있도록 추가
        String username = "iron";
        String deleteCategoryId = categoryService.deleteCategory(categoryId, username);

        return CommonResponse.success(new CategoryIdResponseDto(deleteCategoryId));
    }
}
