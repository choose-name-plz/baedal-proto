package com.nameplz.baedal.domain.review.repository;

import com.nameplz.baedal.domain.review.domain.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    Review findByOrder_IdAndDeletedAtIsNull(UUID orderId);

    List<Review> findAllByUser_UsernameAndDeletedAtIsNull(String username, Pageable pageable);

    /**
     * 특정 가게의 모든 리뷰를 조회
     * Review 엔티티의 Rating 값객체를 이용하여 평균 평점을 계산
     */
    @Query("select r from Review r inner join fetch Order o on r.order = o where o.store.id = :storeId and o.deletedAt is null and r.deletedAt is null and r.isReported = false")
    List<Review> findAllReviewsByStoreId(UUID storeId);
}
