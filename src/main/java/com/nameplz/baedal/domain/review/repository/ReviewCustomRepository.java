package com.nameplz.baedal.domain.review.repository;

import com.nameplz.baedal.domain.review.domain.Review;
import com.nameplz.baedal.domain.review.repository.dto.ReviewScoreWithStoreDto;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface ReviewCustomRepository {

    Review findByOrderIdWithoutDeleted(UUID orderId);

    List<Review> findAllByUsernameWithoutDeleted(String username, Pageable pageable);

    List<Review> findAllByStoreIdWithoutDeleted(UUID storeId, Pageable pageable);

    List<ReviewScoreWithStoreDto> findScoreByStoreIds(List<UUID> storeIds);
}
