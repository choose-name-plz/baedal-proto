package com.nameplz.baedal.domain.order.service;

import com.nameplz.baedal.domain.model.Money;
import com.nameplz.baedal.domain.order.domain.Order;
import com.nameplz.baedal.domain.order.domain.OrderLine;
import com.nameplz.baedal.domain.order.domain.OrderStatus;
import com.nameplz.baedal.domain.order.domain.OrderType;
import com.nameplz.baedal.domain.order.dto.request.CreateOrderRequestDto;
import com.nameplz.baedal.domain.order.dto.request.UpdateOrderStatusRequestDto;
import com.nameplz.baedal.domain.order.dto.response.OrderResponseDto;
import com.nameplz.baedal.domain.order.mapper.OrderMapper;
import com.nameplz.baedal.domain.order.repository.OrderRepository;
import com.nameplz.baedal.domain.store.domain.Store;
import com.nameplz.baedal.domain.user.domain.User;
import com.nameplz.baedal.global.common.exception.GlobalException;
import com.nameplz.baedal.global.common.response.ResultCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderMapper mapper;
    private final OrderRepository orderRepository;

    /**
     * 주문 단건 조회
     */
    public OrderResponseDto getOrder(UUID orderId) {

        Order order = findOrder(orderId);
        return mapper.toOrderResponseDto(order);
    }

    /**
     * 특정 사용자의 주문 목록 조회
     */
    public List<OrderResponseDto> getOrderListByUsername(String username, Pageable pageable) {

        return orderRepository.findAllByUser_UsernameAndDeletedAtIsNull(username, pageable)
                .stream()
                .map(mapper::toOrderResponseDto)
                .toList();
    }

    /**
     * 특정 가게의 주문 목록 조회
     */
    public List<OrderResponseDto> getOrderListByStoreId(UUID storeId, Pageable pageable) {

        return orderRepository.findAllByStore_IdAndDeletedAtIsNull(storeId, pageable)
                .stream()
                .map(mapper::toOrderResponseDto)
                .toList();
    }

    /**
     * 주문 생성
     */
    @Transactional
    public OrderResponseDto createOrder(CreateOrderRequestDto requestDto) {

        // TODO : 주문자 조회
        // TODO : 가게 조회

        Order order = createOrderEntity(requestDto, null, null);
        addOrderLineListInOrder(requestDto, order);

        Order savedOrder = orderRepository.save(order);

        return mapper.toOrderResponseDto(savedOrder);
    }

    private Order createOrderEntity(CreateOrderRequestDto requestDto, User user, Store store) {

        OrderType orderType = OrderType.findByName(requestDto.orderType());

        return Order.create(
                OrderStatus.PAYMENT_WAITING,
                orderType,
                requestDto.comment(),
                requestDto.address(),
                user,
                store
        );
    }

    private void addOrderLineListInOrder(CreateOrderRequestDto request, Order order) {
        request.orderLineList()
                .stream()
                .map(orderLineDto -> OrderLine.create(
                        orderLineDto.quantity(),
                        new Money(orderLineDto.price()),
                        null,
                        order))
                .forEach(order::addOrderLine);
    }

    /**
     * 주문 상태 변경
     */
    @Transactional
    public void updateOrderStatus(UUID orderId, UpdateOrderStatusRequestDto request) {

        OrderStatus orderStatus = OrderStatus.findByName(request.orderStatus());

        Order order = findOrder(orderId);
        order.updateOrderStatus(orderStatus);
    }

    /**
     * 주문 삭제
     */
    @Transactional
    public void cancelOrder(UUID orderId) {

        Order order = findOrder(orderId);
        order.cancelOrder();
    }

    private Order findOrder(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new GlobalException(ResultCase.ORDER_NOT_FOUND));
    }
}
