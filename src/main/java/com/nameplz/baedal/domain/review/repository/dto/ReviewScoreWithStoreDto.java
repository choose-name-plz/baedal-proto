package com.nameplz.baedal.domain.review.repository.dto;

import java.util.UUID;


public record ReviewScoreWithStoreDto
    (
        UUID storeId,
        Double score
    ) {

}
