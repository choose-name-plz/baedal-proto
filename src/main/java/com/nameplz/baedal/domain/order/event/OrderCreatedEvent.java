package com.nameplz.baedal.domain.order.event;

import com.nameplz.baedal.domain.model.Address;
import com.nameplz.baedal.domain.order.domain.OrderStatus;
import com.nameplz.baedal.domain.order.domain.OrderType;
import com.nameplz.baedal.global.event.BaseEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class OrderCreatedEvent extends BaseEvent {

    private final UUID orderId;
    private final String orderer;
    private final UUID storeId;
    private final OrderStatus orderStatus;
    private final OrderType orderType;
    private final Address address;
    private final String comment;
}
