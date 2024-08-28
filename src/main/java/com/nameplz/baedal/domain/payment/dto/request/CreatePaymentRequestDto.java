package com.nameplz.baedal.domain.payment.dto.request;

import com.nameplz.baedal.domain.payment.domain.PaymentMethod;
import com.nameplz.baedal.domain.payment.domain.PaymentStatus;

public record CreatePaymentRequestDto(
        Integer amount,
        String paymentKey,
        PaymentMethod method,
        PaymentStatus status
) {
}
