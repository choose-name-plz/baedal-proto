package com.nameplz.baedal.domain.store.dto.response;

import java.time.LocalDateTime;

public record ProductResponseDto(String storeId, String name, String description, String image,
                                 boolean isPublic, Integer price, LocalDateTime createdAt) {

}
