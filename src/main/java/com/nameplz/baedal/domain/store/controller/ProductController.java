package com.nameplz.baedal.domain.store.controller;

import com.nameplz.baedal.domain.store.dto.request.ProductCreateRequestDto;
import com.nameplz.baedal.domain.store.dto.request.ProductListCreateRequestDto;
import com.nameplz.baedal.domain.store.dto.request.ProductUpdateRequestDto;
import com.nameplz.baedal.domain.store.dto.request.ProductUpdateStatusRequestDto;
import com.nameplz.baedal.domain.store.dto.response.ProductIdListResponseDto;
import com.nameplz.baedal.domain.store.dto.response.ProductIdResponseDto;
import com.nameplz.baedal.domain.store.dto.response.ProductListResponseDto;
import com.nameplz.baedal.domain.store.dto.response.ProductResponseDto;
import com.nameplz.baedal.domain.store.service.ProductService;
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
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Tag(name = "상품")
@RequiredArgsConstructor
@RequestMapping("products")
@RestController
public class ProductController {

    private final ProductService productService;

    /*
        Product 생성
     */
    @Secured({Authority.OWNER, Authority.MASTER})
    @PostMapping
    public CommonResponse<ProductIdResponseDto> createProduct(
        @RequestBody @Validated ProductCreateRequestDto requestDto,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = ((UserDetailsImpl) userDetails).getUser();
        String productId = productService.createProduct(user, requestDto.name(),
            requestDto.description(),
            requestDto.price(), requestDto.image(), requestDto.storeId());
        return CommonResponse.success(new ProductIdResponseDto(productId));
    }

    /*
        Product 일괄 생성
        하나의 가게에 대해서만 일괄저장 하도록 설정
     */
    @Secured({Authority.OWNER, Authority.MASTER})
    @PostMapping("/batch")
    public CommonResponse<ProductIdListResponseDto> createProductList(
        @RequestBody @Validated ProductListCreateRequestDto requestDto,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = ((UserDetailsImpl) userDetails).getUser();
        List<ProductIdResponseDto> productIdList = productService.createProductBatch(
            user,
            requestDto.productList(),
            requestDto.storeId());
        return CommonResponse.success(new ProductIdListResponseDto(productIdList));
    }

    /*
     * 관리자용 Api
     * Product 전체 목록을 갖고 온다.
     * /products?size=10&sort=createdAt,desc
     */
    @Secured({Authority.MASTER})
    @GetMapping
    public CommonResponse<ProductListResponseDto> findProductList(
        @PageableDefault(sort = "createdAt", direction = Direction.DESC) Pageable pageable
    ) {
        List<ProductResponseDto> productList = productService.findProductList(pageable);

        return CommonResponse.success(
            new ProductListResponseDto(pageable.getPageNumber(), productList));
    }

    /*
        Product 개별 조회
     */
    @GetMapping("/{id}")
    public CommonResponse<ProductResponseDto> findProduct(
        @PathVariable("id") UUID productId
    ) {
        ProductResponseDto product = productService.findProductById(productId);
        return CommonResponse.success(product);
    }

    /*
        Product 업데이트
     */
    @Secured({Authority.OWNER, Authority.MASTER})
    @PutMapping("/{id}")
    public CommonResponse<ProductResponseDto> updateProduct(
        @PathVariable("id") UUID productId,
        @RequestBody @Validated ProductUpdateRequestDto requestDto,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = ((UserDetailsImpl) userDetails).getUser();
        ProductResponseDto product = productService.updateProduct(user, productId,
            requestDto.name(),
            requestDto.description(),
            requestDto.image(), requestDto.isPublic());

        return CommonResponse.success(product);
    }


    /*
        Product 상태 변경 on/off
     */
    @Secured({Authority.OWNER, Authority.MASTER})
    @PatchMapping("/{id}/status")
    public CommonResponse<ProductResponseDto> updateProductStatus(
        @PathVariable("id") UUID productId,
        @RequestBody @Validated ProductUpdateStatusRequestDto requestDto,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = ((UserDetailsImpl) userDetails).getUser();
        ProductResponseDto productResponseDto = productService.updateProductStatus(user,
            productId,
            requestDto.isPublic());

        return CommonResponse.success(productResponseDto);
    }

    /*
        Product 삭제
     */
    @Secured({Authority.OWNER, Authority.MASTER})
    @DeleteMapping("/{id}")
    public CommonResponse<ProductIdResponseDto> deleteProduct(
        @PathVariable("id") UUID productId,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = ((UserDetailsImpl) userDetails).getUser();
        String deletedProduct = productService.deleteProduct(productId, user);

        return CommonResponse.success(new ProductIdResponseDto(deletedProduct));
    }

    // 예외 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CommonResponse<EmptyResponseDto>> handleIllegalArgumentException(
        IllegalArgumentException exception) {

        log.error("에러 발생 : {}", exception.getMessage());
        GlobalException e = new GlobalException(ResultCase.INVALID_INPUT);
        return new ResponseEntity<>(CommonResponse.error(e.getResultCase()),
            HttpStatus.BAD_REQUEST);
    }

}
