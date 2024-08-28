package com.nameplz.baedal.domain.store.dto.request;

import java.util.UUID;

public record StoreCreateRequestDto(
    String title, String description, String image, UUID territoryId, UUID categoryId) {

}
