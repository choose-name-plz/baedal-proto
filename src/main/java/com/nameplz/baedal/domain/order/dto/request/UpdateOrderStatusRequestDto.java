package com.nameplz.baedal.domain.order.dto.request;

import com.nameplz.baedal.domain.order.domain.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "주문 상태 변경 요청")
public record UpdateOrderStatusRequestDto(

        @Schema(description = "주문 상태", example = "PAYMENT_WAITING")
        OrderStatus orderStatus
) {
}
