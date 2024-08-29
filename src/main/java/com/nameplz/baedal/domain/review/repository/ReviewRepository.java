package com.nameplz.baedal.domain.review.repository;

import com.nameplz.baedal.domain.review.domain.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID>, ReviewCustomRepository {

    Review findByOrderId(UUID orderId);

    List<Review> findAllByUser_Username(String username, Pageable pageable);
}
