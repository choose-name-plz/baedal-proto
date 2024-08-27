package com.nameplz.baedal.domain.category.controller;

import com.nameplz.baedal.domain.category.dto.request.CategoryAddRequestDto;
import com.nameplz.baedal.domain.category.dto.response.CategoryCreateResponseDto;
import com.nameplz.baedal.domain.category.dto.response.CategoryResponseDto;
import com.nameplz.baedal.domain.category.service.CategoryService;
import com.nameplz.baedal.global.common.response.CommonResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("category")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CommonResponse<CategoryCreateResponseDto>> addCategory(
        @RequestBody CategoryAddRequestDto requestBody) {
        //TODO: 마스터만 호출할 수 있도록 추가
        String categoryId = categoryService.addCategory(requestBody.name());
        return new ResponseEntity<>(
            CommonResponse.success(new CategoryCreateResponseDto(categoryId)), HttpStatus.CREATED);
    }

    @GetMapping
    public CommonResponse<List<CategoryResponseDto>> getCategoryByName(
        @RequestParam(value = "name", required = false) String name
    ) {
        List<CategoryResponseDto> category = categoryService.getCategoryList(name);
        return CommonResponse.success(category);
    }
}
