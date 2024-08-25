package com.nameplz.baedal.domain.user.domain.dto.response;

import com.nameplz.baedal.domain.user.domain.User;

public class UserResponse {
    private String username;
    private String nickname;
    private String email;
    private String role;
    private Boolean isPublic;

    public UserResponse(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.isPublic = user.getIsPublic();
    }
}