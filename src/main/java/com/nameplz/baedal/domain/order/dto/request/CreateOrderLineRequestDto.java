package com.nameplz.baedal.domain.order.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "주문목록 생성 요청")
public record CreateOrderLineRequestDto(

        @Schema(description = "메뉴 ID", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID menuId,

        @Schema(description = "수량", example = "2")
        @NotNull(message = "수량은 필수입니다.")
        Integer quantity,

        @Schema(description = "가격", example = "10000")
        @NotNull(message = "가격은 필수입니다.")
        Integer price
) {
}
