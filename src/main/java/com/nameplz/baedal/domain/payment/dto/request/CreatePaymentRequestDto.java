package com.nameplz.baedal.domain.payment.dto.request;

import com.nameplz.baedal.domain.payment.domain.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public record CreatePaymentRequestDto(

        @Schema(description = "결제 금액", example = "10000")
        @Range(message = "금액은 0 이상이어야 합니다.")
        @NotNull(message = "금액은 필수입니다.")
        Integer amount,

        @Schema(description = "결제 방식", example = "CARD")
        PaymentMethod method,

        @Schema(description = "결제자", example = "johndoe")
        @NotNull(message = "결제자는 필수입니다.")
        String username
) {
}
