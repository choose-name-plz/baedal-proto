package com.nameplz.baedal.domain.payment.dto.response;

import com.nameplz.baedal.domain.payment.domain.PaymentMethod;
import com.nameplz.baedal.domain.payment.domain.PaymentStatus;

import java.util.UUID;

public record PaymentResponseDto(
        UUID paymentId,
        Integer amount,
        PaymentMethod method,
        PaymentStatus status
) {
}
