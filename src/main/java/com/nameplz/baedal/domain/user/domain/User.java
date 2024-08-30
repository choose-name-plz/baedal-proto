package com.nameplz.baedal.domain.user.domain;

import com.nameplz.baedal.domain.model.BaseEntity;
import com.nameplz.baedal.domain.user.dto.request.UserUpdateRequestDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "p_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @Column(name = "username", length = 100)
    private String username;

    @Column(name = "nickname", length = 100)
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)  // This will store the enum name as a string in the database
    @Column(name = "role")
    private UserRole role;

    @Column(name = "is_public")
    private boolean isPublic;

    public static User create(String username, String password) {

        User user = new User();

        user.username = username;
        user.password = password;
        user.role = UserRole.CUSTOMER;

        return user;
    }

    public void update(UserUpdateRequestDto request) {
        this.nickname = request.nickname();
        this.email = request.email();
        // 비밀번호는 이미 인코딩된 상태로 받아서 처리
        this.password = request.password(); // 비밀번호는 인코딩된 상태로 설정
        this.isPublic = request.isPublic();
    }

    // 비밀번호 업데이트 메서드
    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
