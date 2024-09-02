package com.nameplz.baedal.domain.payment.dto.response;

public record ExternalPaymentResponseDto(
        boolean isSuccess,
        String paymentKey
) {
}
