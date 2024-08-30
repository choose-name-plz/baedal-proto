package com.nameplz.baedal.domain.user.dto.response;

import com.nameplz.baedal.domain.user.domain.User;
import com.nameplz.baedal.domain.user.domain.UserRole;

public record UserUpdateResponseDto(
    String username,
    String nickname,
    String email,
    UserRole role,
    Boolean isPublic
){}