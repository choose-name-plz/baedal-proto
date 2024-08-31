package com.nameplz.baedal.domain.order.mapper;

import com.nameplz.baedal.domain.order.domain.Order;
import com.nameplz.baedal.domain.order.domain.OrderLine;
import com.nameplz.baedal.domain.order.dto.response.OrderLineResponseDto;
import com.nameplz.baedal.domain.order.dto.response.OrderResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface OrderMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "orderLineId", source = "orderLine.id")
    OrderLineResponseDto toOrderLineResponseDto(OrderLine orderLine);

    @Mapping(target = "storeId", source = "order.store.id")
    @Mapping(target = "userId", source = "order.user.username")
    @Mapping(target = "orderId", source = "order.id")
    @Mapping(
            target = "orderLineList",
            expression = "java(order.getOrderLines().stream().map(orderLine -> toOrderLineResponseDto(orderLine)).toList())"
    )
    OrderResponseDto toOrderResponseDto(Order order);
}
