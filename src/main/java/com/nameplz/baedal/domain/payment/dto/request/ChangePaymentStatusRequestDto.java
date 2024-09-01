package com.nameplz.baedal.domain.payment.dto.request;

import com.nameplz.baedal.domain.payment.domain.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record ChangePaymentStatusRequestDto(

        @Schema(description = "결제 상태", example = "SUCCESS")
        PaymentStatus status
) {
}
