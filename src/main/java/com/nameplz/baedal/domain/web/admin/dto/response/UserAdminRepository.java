package com.nameplz.baedal.domain.web.admin.dto.response;

import com.nameplz.baedal.domain.user.domain.UserRole;
import java.time.LocalDateTime;

public record UserAdminRepository(
    String username, UserRole role,
    LocalDateTime deletedAt, LocalDateTime createdAt
) {

}
