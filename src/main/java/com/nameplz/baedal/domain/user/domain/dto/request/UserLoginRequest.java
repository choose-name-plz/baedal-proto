package com.nameplz.baedal.domain.user.domain.dto.request;

import lombok.Getter;

@Getter
public class UserLoginRequest {
    private String username;
    private String password;
}