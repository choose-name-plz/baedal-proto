package com.nameplz.baedal.domain.order.dto.response;

import com.nameplz.baedal.domain.model.Address;
import com.nameplz.baedal.domain.order.domain.OrderStatus;
import com.nameplz.baedal.domain.order.domain.OrderType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Schema(description = "주문 응답")
public record OrderResponseDto(

        @Schema(description = "주문 ID", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID orderId,

        @Schema(description = "주문 상태", example = "PAYMENT_WAITING")
        OrderStatus orderStatus,

        @Schema(description = "주문 타입", example = "ONLINE")
        OrderType orderType,

        @Schema(description = "주문 요청 사항", example = "2인분 같은 1인분으로 부탁드려요.")
        String comment,

        @Schema(description = "주소 정보")
        Address address,

        @Schema(description = "주문자 ID", example = "123e4567-e89b-12d3-a456-426614174000")
        String userId,

        @Schema(description = "가게 ID", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID storeId,

        @Schema(description = "주문 상품 목록")
        List<OrderLineResponseDto> orderLineList,

        @Schema(description = "주문 생성 일자", example = "2024-08-31T06:00:48.416Z")
        LocalDateTime createdAt
) {
}
