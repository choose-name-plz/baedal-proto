package com.nameplz.baedal.domain.order.dto.request;

import com.nameplz.baedal.domain.payment.domain.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "주문 요청 시 포함되는 결제 정보")
public record PaymentRequestDto(

        @Schema(description = "결제 금액", example = "10000")
        @NotNull(message = "결제 금액은 필수입니다.")
        Integer amount,

        @Schema(description = "결제 방식", example = "CARD")
        @NotNull(message = "결제 방식은 필수입니다.")
        PaymentMethod method
) {
}
