package com.nameplz.baedal.domain.payment.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public enum PaymentMethod {
    CARD
}
