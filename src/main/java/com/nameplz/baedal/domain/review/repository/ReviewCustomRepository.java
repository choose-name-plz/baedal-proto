package com.nameplz.baedal.domain.review.repository;

import com.nameplz.baedal.domain.review.domain.Review;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ReviewCustomRepository {

    Review findByOrderIdWithoutDeleted(UUID orderId);

    List<Review> findAllByUsernameWithoutDeleted(String username, Pageable pageable);

    List<Review> findAllByStoreIdWithoutDeleted(UUID storeId, Pageable pageable);
}
