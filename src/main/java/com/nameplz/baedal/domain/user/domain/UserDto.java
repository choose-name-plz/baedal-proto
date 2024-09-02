package com.nameplz.baedal.domain.user.domain;

// LocalDateTime 을 제거한 redis 캐싱용 Dto
public record UserDto(
    String username,
    String password,
    UserRole role
) {

}
