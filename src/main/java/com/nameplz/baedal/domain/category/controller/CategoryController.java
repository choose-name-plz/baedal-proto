package com.nameplz.baedal.domain.category.controller;

import com.nameplz.baedal.domain.category.dto.request.CategoryAddRequestDto;
import com.nameplz.baedal.domain.category.dto.request.CategoryUpdateRequestDto;
import com.nameplz.baedal.domain.category.dto.response.CategoryIdResponseDto;
import com.nameplz.baedal.domain.category.dto.response.CategoryResponseDto;
import com.nameplz.baedal.domain.category.service.CategoryService;
import com.nameplz.baedal.domain.user.domain.UserRole.Authority;
import com.nameplz.baedal.global.common.response.CommonResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "카테고리")
@RequiredArgsConstructor
@RequestMapping("category")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    /*
        카테고리 생성
     */
    @Secured({Authority.MASTER})
    @PostMapping
    public CommonResponse<CategoryIdResponseDto> addCategory(
        @RequestBody @Validated CategoryAddRequestDto requestBody) {
        String categoryId = categoryService.addCategory(requestBody.name());
        return CommonResponse.success(new CategoryIdResponseDto(categoryId));
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
    @Secured({Authority.MASTER})
    @PutMapping("/{id}")
    public CommonResponse<CategoryResponseDto> updateCategory(
        @PathVariable("id") UUID categoryId,
        @RequestBody @Validated CategoryUpdateRequestDto requestBody
    ) {

        CategoryResponseDto categoryResponseDto = categoryService.updateCategory(categoryId,
            requestBody.name());

        return CommonResponse.success(categoryResponseDto);
    }

    /*
        카테고리 삭제
     */
    @Secured({Authority.MASTER})
    @DeleteMapping("/{id}")
    public CommonResponse<CategoryIdResponseDto> deleteCategory(
        @PathVariable("id") UUID categoryId,
        @AuthenticationPrincipal UserDetails userDetails
    ) {

        String username = userDetails.getUsername();
        String deleteCategoryId = categoryService.deleteCategory(categoryId, username);

        return CommonResponse.success(new CategoryIdResponseDto(deleteCategoryId));
    }

    /*
     * 카테고리 삭제 취소
     */
    @Secured({Authority.MASTER})
    @PatchMapping("/{id}/delete-cancel")
    public CommonResponse<CategoryIdResponseDto> cancelDeleteCategory(
        @PathVariable("id") UUID categoryId
    ) {
        //TODO: 마스터만 호출할 수 있도록 추가
        String deletedCategoryId = categoryService.cancelCategoryId(categoryId);
        return CommonResponse.success(new CategoryIdResponseDto(deletedCategoryId));
    }

}
