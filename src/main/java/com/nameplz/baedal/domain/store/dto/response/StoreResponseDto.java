package com.nameplz.baedal.domain.store.dto.response;

import com.nameplz.baedal.domain.store.domain.StoreStatus;

public record StoreResponseDto(
    String id, String title, String description, String image, StoreStatus status,
    String categoryName
) {

}
