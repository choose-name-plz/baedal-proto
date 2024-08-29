package com.nameplz.baedal.domain.review.controller;

import com.nameplz.baedal.domain.review.dto.request.CreateReviewRequestDto;
import com.nameplz.baedal.domain.review.dto.request.UpdateReviewRequestDto;
import com.nameplz.baedal.domain.review.dto.response.ReviewResponseDto;
import com.nameplz.baedal.domain.review.service.ReviewService;
import com.nameplz.baedal.global.common.response.CommonResponse;
import com.nameplz.baedal.global.common.response.EmptyResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@Tag(name = "리뷰")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 주문 아이디로 리뷰 단건 조회
     */
    @GetMapping("/orders/{orderId}")
    public CommonResponse<ReviewResponseDto> getReview(@PathVariable UUID orderId) {

        ReviewResponseDto response = reviewService.getReviewByOrderId(orderId);

        return CommonResponse.success(response);
    }

    /**
     * 유저 아이디로 리뷰 리스트 조회
     */
    @GetMapping("/users/{userId}")
    public CommonResponse<List<ReviewResponseDto>> getReview(
            @PathVariable String userId,
            @PageableDefault(sort = "createdAt", direction = DESC) Pageable pageable
    ) {

        List<ReviewResponseDto> response = reviewService.getReviewListByUsername(userId, pageable);

        return CommonResponse.success(response);
    }

    /**
     * 가게 아이디로 리뷰 리스트 조회
     */
    @GetMapping("/stores/{storeId}")
    public CommonResponse<List<ReviewResponseDto>> getReview(
            @PathVariable UUID storeId,
            @PageableDefault(sort = "createdAt", direction = DESC) Pageable pageable
    ) {

        List<ReviewResponseDto> response = reviewService.getReviewListByStoreId(storeId);

        return CommonResponse.success(response);
    }

    /**
     * 리뷰 생성
     */
    @PostMapping("/orders/{orderId}")
    public CommonResponse<ReviewResponseDto> createReview(
            @PathVariable UUID orderId,
            @Validated @RequestBody CreateReviewRequestDto request
    ) {

        // TODO : 유저 가져오기
        ReviewResponseDto response = reviewService.createReview(request, "username", orderId);

        return CommonResponse.success(response);
    }

    /**
     * 리뷰 수정
     */
    @PutMapping("/{reviewId}")
    public CommonResponse<EmptyResponseDto> updateReview(
            @PathVariable UUID reviewId,
            @Validated @RequestBody UpdateReviewRequestDto request
    ) {

        reviewService.updateReview(request, reviewId);

        return CommonResponse.success();
    }

    /**
     * 리뷰 삭제
     */
    @DeleteMapping("/{reviewId}")
    public CommonResponse<EmptyResponseDto> deleteReview(@PathVariable UUID reviewId) {

        // TODO : 유저 가져오기
        reviewService.deleteReview(reviewId, "username");

        return CommonResponse.success();
    }
}
