package com.nameplz.baedal.domain.order.dto.response;

import com.nameplz.baedal.domain.model.Money;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "주문목록 응답")
public record OrderLineResponseDto(

        @Schema(description = "주문 목록 ID", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID orderLineId,

        @Schema(description = "주문 ID", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID orderId,

        @Schema(description = "상품 ID", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID productId,

        @Schema(description = "상품 수량", example = "2")
        Integer quantity,

        @Schema(description = "가격", example = "10000")
        Money amount
) {
}
