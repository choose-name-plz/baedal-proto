package com.nameplz.baedal.domain.user.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public enum UserRole {
    CUSTOMER, OWNER, MASTER
}
