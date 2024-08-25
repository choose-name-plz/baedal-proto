package com.nameplz.baedal.domain.user.domain.dto.request;

import lombok.Getter;

@Getter
public class UserUpdateRequest {
    private String nickname;
    private String email;
    private String password;
    private String address;
    private Boolean isPublic;
}
