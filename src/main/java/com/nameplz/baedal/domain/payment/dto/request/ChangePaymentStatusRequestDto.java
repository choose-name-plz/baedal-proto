package com.nameplz.baedal.domain.payment.dto.request;

import com.nameplz.baedal.domain.payment.domain.PaymentStatus;

public record ChangePaymentStatusRequestDto(
        PaymentStatus status
) {
}
