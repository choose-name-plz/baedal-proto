package com.nameplz.baedal.domain.store.dto.request;

import com.nameplz.baedal.domain.store.domain.StoreStatus;
import java.util.UUID;

public record StoreUpdateRequestDto(
    String title, String description, String image, StoreStatus status, UUID categoryId,
    UUID territoryId
) {

}
