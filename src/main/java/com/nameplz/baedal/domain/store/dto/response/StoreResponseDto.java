package com.nameplz.baedal.domain.store.dto.response;

import com.nameplz.baedal.domain.store.domain.StoreStatus;
import java.time.LocalDateTime;

public record StoreResponseDto(
    String id, String title, String description, String image, StoreStatus status,
    String categoryName, LocalDateTime createdAt
) {

}
