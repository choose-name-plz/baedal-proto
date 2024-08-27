package com.nameplz.baedal.domain.order.dto.request;

import java.util.UUID;

public record CreateOrderLineRequestDto(
        UUID menuId,
        Integer quantity,
        Integer price
) {
}
