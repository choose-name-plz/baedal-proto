package com.nameplz.baedal.domain.payment.domain;

import com.nameplz.baedal.domain.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name = "p_payment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "price")
    private Integer price;

    @Column(name = "method", length = 100)
    @Embedded
    private PaymentMethod method;

    @Column(name = "status", length = 100)
    @Embedded
    private PaymentStatus status;

    public static Payment create(Integer price, PaymentMethod method, PaymentStatus status) {

        Payment payment = new Payment();

        payment.price = price;
        payment.method = method;
        payment.status = status;

        return payment;
    }
}
