package com.nameplz.baedal.domain.user.dto.request;

public record UserUpdateRequestDto (
    String nickname,
    String email,
    String password,
    String address,
    Boolean isPublic
){}