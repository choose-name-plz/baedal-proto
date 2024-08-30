package com.nameplz.baedal.domain.web.admin.dto.response;

import java.time.LocalDateTime;

public record CategoryAdminResponseDto(String id, String name, LocalDateTime deletedAt,
                                       LocalDateTime createdAt) {

}
