package com.nameplz.baedal.domain.store.controller;

import com.nameplz.baedal.domain.store.domain.StoreStatus;
import com.nameplz.baedal.domain.store.dto.request.StoreCreateRequestDto;
import com.nameplz.baedal.domain.store.dto.request.StoreUpdateCategoryRequestDto;
import com.nameplz.baedal.domain.store.dto.request.StoreUpdateRequestDto;
import com.nameplz.baedal.domain.store.dto.request.StoreUpdateStatusRequestDto;
import com.nameplz.baedal.domain.store.dto.request.StoreUpdateTerritoryRequestDto;
import com.nameplz.baedal.domain.store.dto.response.ProductListResponseDto;
import com.nameplz.baedal.domain.store.dto.response.ProductResponseDto;
import com.nameplz.baedal.domain.store.dto.response.StoreIdResponseDto;
import com.nameplz.baedal.domain.store.dto.response.StoreListResponseDto;
import com.nameplz.baedal.domain.store.dto.response.StoreResponseDto;
import com.nameplz.baedal.domain.store.service.ProductService;
import com.nameplz.baedal.domain.store.service.StoreService;
import com.nameplz.baedal.domain.user.domain.User;
import com.nameplz.baedal.domain.user.domain.UserRole.Authority;
import com.nameplz.baedal.global.common.exception.GlobalException;
import com.nameplz.baedal.global.common.response.CommonResponse;
import com.nameplz.baedal.global.common.response.EmptyResponseDto;
import com.nameplz.baedal.global.common.response.ResultCase;
import com.nameplz.baedal.global.common.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "상점")
@RequiredArgsConstructor
@RequestMapping("stores")
@RestController
public class StoreController {


    private final StoreService storeService;
    private final ProductService productService;

    /*
        Store 생성
     */
    @Secured({Authority.CUSTOMER, Authority.MASTER})
    @PostMapping
    public CommonResponse<StoreIdResponseDto> createStore(
        @RequestBody @Validated StoreCreateRequestDto requestDto,
        @AuthenticationPrincipal UserDetails userDetails) {

        User user = ((UserDetailsImpl) userDetails).getUser();

        String storeId = storeService.createStore(
            requestDto.title(),
            requestDto.description(),
            requestDto.image(),
            requestDto.territoryId(),
            requestDto.categoryId(),
            user
        );

        return CommonResponse.success(new StoreIdResponseDto(storeId));
    }

    /*
        Store 업데이트
     */
    @Secured({Authority.OWNER, Authority.MASTER})
    @PutMapping("/{id}")
    public CommonResponse<StoreResponseDto> updateStore(
        @PathVariable("id") UUID storeId,
        @RequestBody @Validated StoreUpdateRequestDto requestDto,
        @AuthenticationPrincipal UserDetails userDetails
    ) {

        User user = ((UserDetailsImpl) userDetails).getUser();
        StoreResponseDto storeResponseDto = storeService.updateStore(user, storeId,
            requestDto.territoryId(), requestDto.categoryId(),
            requestDto.title(), requestDto.description(), requestDto.image(), requestDto.status());

        return CommonResponse.success(storeResponseDto);
    }

    /*
     * 가게 상태 변경
     */
    @Secured({Authority.OWNER, Authority.MASTER})
    @PatchMapping("/{id}/status")
    public CommonResponse<StoreResponseDto> updateStoreStatus(
        @PathVariable("id") UUID storeId,
        @RequestBody StoreUpdateStatusRequestDto requestDto,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = ((UserDetailsImpl) userDetails).getUser();
        StoreResponseDto storeResponseDto = storeService.updateStoreStatus(user, storeId,
            requestDto.status());

        return CommonResponse.success(storeResponseDto);
    }

    /*
     * 가게 카테고리 변경
     */
    @Secured({Authority.OWNER, Authority.MASTER})
    @PatchMapping("/{id}/category")
    public CommonResponse<StoreResponseDto> updateStoreCategory(
        @PathVariable("id") UUID storeId,
        @RequestBody StoreUpdateCategoryRequestDto requestDto,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = ((UserDetailsImpl) userDetails).getUser();
        StoreResponseDto storeResponseDto = storeService.updateStoreTerritory(user, storeId,
            requestDto.categoryId());

        return CommonResponse.success(storeResponseDto);
    }


    /*
     * 가게 지역 변경
     */
    @Secured({Authority.OWNER, Authority.MASTER})
    @PatchMapping("/{id}/territory")
    public CommonResponse<StoreResponseDto> updateStoreTerritory(
        @PathVariable("id") UUID storeId,
        @RequestBody StoreUpdateTerritoryRequestDto requestDto,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = ((UserDetailsImpl) userDetails).getUser();
        StoreResponseDto storeResponseDto = storeService.updateStoreTerritory(user, storeId,
            requestDto.territoryId());

        return CommonResponse.success(storeResponseDto);
    }

    /*
        가게 삭제
     */
    @Secured({Authority.OWNER, Authority.MASTER})
    @DeleteMapping("/{id}")
    public CommonResponse<StoreIdResponseDto> deleteStore(
        @PathVariable("id") UUID storeId,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = ((UserDetailsImpl) userDetails).getUser();
        String deletedStoreId = storeService.deleteStore(user, storeId);
        return CommonResponse.success(new StoreIdResponseDto(deletedStoreId));
    }

    /*
        가게 단일 검색
     */
    @GetMapping("/{id}")
    public CommonResponse<StoreResponseDto> findStoreById(
        @PathVariable("id") UUID storeId
    ) {
        StoreResponseDto storeInfo = storeService.findStoreById(storeId);
        return CommonResponse.success(storeInfo);
    }


    /*
     * 가게 목록 검색
     * /stores/{id}/products?size=10&page=0&sort=(createdAt, updatedAt),desc
     */
    @GetMapping
    public CommonResponse<StoreListResponseDto> findStoreList(
        @RequestParam(value = "category", required = false) UUID categoryId,
        @RequestParam(value = "title", required = false) String title,
        @RequestParam(value = "status", required = false) StoreStatus status,
        @PageableDefault(sort = "createdAt", direction = Direction.DESC) Pageable pageable
    ) {

        List<StoreResponseDto> storeList = storeService.findStoreList(title, categoryId, status,
            pageable);

        return CommonResponse.success(
            new StoreListResponseDto(pageable.getPageNumber(), storeList));
    }

    /*
     * 가게에 속한 상품 목록 검색
     * /stores/{id}/products?size=10&page=0&sort=(createdAt, updatedAt),desc
     */
    @GetMapping("/{id}/products")
    public CommonResponse<ProductListResponseDto> findProductListByStore(
        @PathVariable("id") UUID id,
        @RequestParam(value = "all", defaultValue = "true") boolean hideNotPublic,
        @PageableDefault(sort = "createdAt", direction = Direction.DESC) Pageable pageable
    ) {
        List<ProductResponseDto> productListByStore = productService.findProductListByStore(id,
            hideNotPublic,
            pageable);
        return CommonResponse.success(
            new ProductListResponseDto(pageable.getPageNumber(), productListByStore));
    }

    // 예외 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CommonResponse<EmptyResponseDto>> handleIllegalArgumentException(
        IllegalArgumentException exception) {

        log.info("에러 발생 : {}", exception.getMessage());
        GlobalException e = new GlobalException(ResultCase.INVALID_INPUT);
        return new ResponseEntity<>(CommonResponse.error(e.getResultCase()),
            HttpStatus.BAD_REQUEST);
    }
}
