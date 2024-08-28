package com.nameplz.baedal.domain.payment.dto.response;

import com.nameplz.baedal.domain.model.Money;
import com.nameplz.baedal.domain.payment.domain.PaymentMethod;
import com.nameplz.baedal.domain.payment.domain.PaymentStatus;

import java.util.UUID;

public record PaymentResponseDto(
        UUID paymentId,
        Money amount,
        PaymentMethod method,
        PaymentStatus status
) {
}
