package com.nameplz.baedal.domain.store.dto.response;

public record ProductResponseDto(String storeId, String name, String description, String image,
                                 boolean isPublic, Integer price) {

}
