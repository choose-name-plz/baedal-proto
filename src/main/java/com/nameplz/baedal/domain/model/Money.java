package com.nameplz.baedal.domain.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class Money {

    private Integer amount;

    public Money add(Money money) {
        return new Money(this.amount + money.amount);
    }

    public Money subtract(Money money) {
        return new Money(this.amount - money.amount);
    }

    public Money multiply(int multiplier) {
        return new Money(this.amount * multiplier);
    }

    @Override
    public String toString() {
        return String.valueOf(amount);
    }
}
