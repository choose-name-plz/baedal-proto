package com.nameplz.baedal.domain.store.dto.request;

public record ProductCreateRequestDto(
    String name, String description, String image, Integer price, String storeId
) {

}
