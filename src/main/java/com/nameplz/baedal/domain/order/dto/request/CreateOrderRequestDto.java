package com.nameplz.baedal.domain.order.dto.request;

import com.nameplz.baedal.domain.model.Address;
import com.nameplz.baedal.domain.order.domain.OrderType;

import java.util.List;
import java.util.UUID;

public record CreateOrderRequestDto(
        String orderer,
        UUID storeId,
        OrderType orderType,
        String comment,
        Address address,
        List<CreateOrderLineRequestDto> orderLineList
) {
}
