package com.nameplz.baedal.domain.review.service;

import com.nameplz.baedal.domain.review.domain.Rating;
import com.nameplz.baedal.domain.review.domain.Review;
import com.nameplz.baedal.domain.review.dto.request.CreateReviewRequestDto;
import com.nameplz.baedal.domain.review.dto.request.UpdateReviewRequestDto;
import com.nameplz.baedal.domain.review.dto.response.ReviewResponseDto;
import com.nameplz.baedal.domain.review.mapper.ReviewMapper;
import com.nameplz.baedal.domain.review.repository.ReviewRepository;
import com.nameplz.baedal.global.common.exception.GlobalException;
import com.nameplz.baedal.global.common.response.ResultCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewMapper mapper;
    private final ReviewRepository reviewRepository;

    /**
     * 주문 아이디로 리뷰 단건 조회 - 삭제되지 않은 리뷰만 조회
     */
    public ReviewResponseDto getReviewByOrderIdWithoutDeleted(UUID orderId) {
        Review review = reviewRepository.findByOrderIdWithoutDeleted(orderId);
        return mapper.toReviewResponseDto(review);
    }

    /**
     * 주문 아이디로 리뷰 단건 조회 - 삭제된 리뷰도 조회
     */
    public ReviewResponseDto getReviewByOrderIdWithDeleted(UUID orderId) {
        Review review = reviewRepository.findByOrderId(orderId);
        return mapper.toReviewResponseDto(review);
    }

    /**
     * 유저 아이디로 리뷰 리스트 조회
     */
    public List<ReviewResponseDto> getReviewListByUsernameWithoutDeleted(String username, Pageable pageable) {

        return reviewRepository.findAllByUsernameWithoutDeleted(username, pageable)
                .stream()
                .map(mapper::toReviewResponseDto)
                .toList();
    }

    /**
     * 유저 아이디로 리뷰 리스트 조회
     */
    public List<ReviewResponseDto> getReviewListByUsernameWithDeleted(String username, Pageable pageable) {

        return reviewRepository.findAllByUser_Username(username, pageable)
                .stream()
                .map(mapper::toReviewResponseDto)
                .toList();
    }

    /**
     * 가게 아이디로 리뷰 리스트 조회
     */
    public List<ReviewResponseDto> getReviewListByStoreIdWithoutDeleted(UUID storeId, Pageable pageable) {

        return reviewRepository.findAllByStoreIdWithoutDeleted(storeId, pageable)
                .stream()
                .map(mapper::toReviewResponseDto)
                .toList();
    }

    /**
     * 리뷰 생성
     */
    @Transactional
    public ReviewResponseDto createReview(CreateReviewRequestDto request, String username, UUID orderId) {

        // TODO : 유저 가져오기
        // TODO : 주문 가져오기

        Review review = Review.create(
                request.content(),
                new Rating(request.rating()),
                request.isReported(),
                null,
                null);

        Review savedReview = reviewRepository.save(review);

        return mapper.toReviewResponseDto(savedReview);
    }

    /**
     * 리뷰 수정
     */
    @Transactional
    public void updateReview(UpdateReviewRequestDto request, UUID reviewId) {
        Review review = findReviewById(reviewId);
        review.update(new Rating(request.rating()), request.isReported(), request.content());
    }

    /**
     * 리뷰 삭제
     */
    @Transactional
    public void deleteReview(UUID reviewId, String username) {
        Review review = findReviewById(reviewId);
        review.deleteEntity(username);
    }

    private Review findReviewById(UUID reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new GlobalException(ResultCase.REVIEW_NOT_FOUND));
    }
}
