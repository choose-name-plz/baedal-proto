package com.nameplz.baedal.domain.web.admin.dto.response;

import com.nameplz.baedal.domain.store.domain.StoreStatus;
import java.time.LocalDateTime;

public record StoreAdminResponseDto(
    String id,

    String title,
    String description,
    StoreStatus status,

    LocalDateTime deletedAt,
    LocalDateTime createdAt
) {

}
