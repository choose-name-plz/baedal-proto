package com.nameplz.baedal.domain.payment.dto.response;

import com.nameplz.baedal.domain.payment.domain.PaymentMethod;
import com.nameplz.baedal.domain.payment.domain.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record PaymentResponseDto(

        @Schema(description = "결제 ID", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID paymentId,

        @Schema(description = "결제자", example = "johndoe")
        String username,

        @Schema(description = "결제 금액", example = "10000")
        Integer amount,

        @Schema(description = "결제 방식", example = "CARD")
        PaymentMethod method,

        @Schema(description = "결제 상태", example = "SUCCESS")
        PaymentStatus status
) {
}
