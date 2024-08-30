package com.nameplz.baedal.domain.web.admin.dto.response;

import java.time.LocalDateTime;

public record TerritoryAdminResponseDto(
    String id, String name, LocalDateTime deletedAt,
    LocalDateTime createdAt
) {

}
