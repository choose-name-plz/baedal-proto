package com.nameplz.baedal.domain.payment.domain;

import com.nameplz.baedal.domain.model.BaseEntity;
import com.nameplz.baedal.domain.model.Money;
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

    @Column(name = "amount")
    @Embedded
    private Money amount;

    // 토스페이먼트 기준 결제키
    @Column(name = "payment_key")
    private String paymentKey;

    @Column(name = "method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "username")
    private String username;

    /**
     * 외부 결제 모듈의 결과를 저장
     */
    public static Payment create(Money amount, PaymentMethod method, String username) {

        Payment payment = new Payment();

        payment.amount = amount;
        payment.method = method;
        payment.username = username;
        payment.status = PaymentStatus.PENDING;
        payment.createdUser = username;

        return payment;
    }

    public void changePaymentStatus(PaymentStatus status) {
        this.status = status;
    }
}
