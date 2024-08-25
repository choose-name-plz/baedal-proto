package com.nameplz.baedal.domain.user.domain.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupResponse {
    private String username;

    public UserSignupResponse(String username) {
        this.username = username;
    }
}

   