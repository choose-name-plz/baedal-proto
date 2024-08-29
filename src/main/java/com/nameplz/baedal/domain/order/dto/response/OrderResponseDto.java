package com.nameplz.baedal.domain.order.dto.response;

import com.nameplz.baedal.domain.model.Address;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponseDto(
        UUID orderId,
        String orderStatus,
        String orderType,
        String comment,
        Address address,
        String userId,
        UUID storeId,
        List<OrderLineResponseDto> orderLineList,
        LocalDateTime createdAt
) {
}
