package com.nameplz.baedal.domain.order.dto.response;

import com.nameplz.baedal.domain.model.Money;

import java.util.UUID;

public record OrderLineResponseDto(
        UUID orderLineId,
        UUID orderId,
        UUID productId,
        Integer quantity,
        Money amount
) {
}
