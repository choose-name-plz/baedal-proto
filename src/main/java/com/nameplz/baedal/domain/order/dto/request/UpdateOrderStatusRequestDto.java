package com.nameplz.baedal.domain.order.dto.request;

import com.nameplz.baedal.domain.order.domain.OrderStatus;

public record UpdateOrderStatusRequestDto(
        OrderStatus orderStatus
) {
}
