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

    /**
     * 외부에서 결제 성공 시 Payment 객체를 생성
     */
    public static Payment create(Money amount, String paymentKey, PaymentMethod method) {

        Payment payment = new Payment();

        payment.amount = amount;
        payment.paymentKey = paymentKey;
        payment.method = method;
        payment.status = PaymentStatus.PAYMENT_SUCCESS;

        return payment;
    }

    public void changePaymentStatus(PaymentStatus status) {
        this.status = status;
    }
}
