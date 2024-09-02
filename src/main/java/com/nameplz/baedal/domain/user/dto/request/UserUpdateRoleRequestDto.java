package com.nameplz.baedal.domain.user.dto.request;

import com.nameplz.baedal.domain.user.domain.UserRole;

public record UserUpdateRoleRequestDto(
    UserRole role
) {

}
