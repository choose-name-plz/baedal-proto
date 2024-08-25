package com.nameplz.baedal.domain.user.domain;

import lombok.Getter;

@Getter
public enum UserRole {
    // CUSTOMER is an enum constant.
    // "ROLE_CUSTOMER" is the associated string value (often used for role-based access control in Spring Security).

    CUSTOMER("ROLE_CUSTOMER"),
    OWNER("ROLE_OWNER"),
    MANAGER("ROLE_MANAGER"),
    MASTER("ROLE_MASTER");

    private final String authority;

    UserRole(String authority) {
        this.authority = authority;
    }
}
