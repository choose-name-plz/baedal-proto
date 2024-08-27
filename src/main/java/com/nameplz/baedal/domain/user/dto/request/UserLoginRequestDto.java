package com.nameplz.baedal.domain.user.dto.request;

public record UserLoginRequestDto (
    String username,
    String password
){}
