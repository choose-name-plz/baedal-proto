package com.nameplz.baedal.domain.user.domain;

import com.nameplz.baedal.domain.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
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

    // lombok 에서 인식 안됨?
    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
    public Boolean getIsPublic() {
        return isPublic;
    }

    public static User create(String username, String password) {

        User user = new User();

        user.username = username;
        user.password = password;

        return user;
    }


}
