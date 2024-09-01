package com.nameplz.baedal.domain.order.service;

import com.nameplz.baedal.domain.model.Money;
import com.nameplz.baedal.domain.order.domain.Order;
import com.nameplz.baedal.domain.order.domain.OrderLine;
import com.nameplz.baedal.domain.order.domain.OrderStatus;
import com.nameplz.baedal.domain.order.dto.request.CreateOrderLineRequestDto;
import com.nameplz.baedal.domain.order.dto.request.CreateOrderRequestDto;
import com.nameplz.baedal.domain.order.dto.request.PaymentRequestDto;
import com.nameplz.baedal.domain.order.event.OrderCreatedEvent;
import com.nameplz.baedal.domain.payment.domain.PaymentStatus;
import com.nameplz.baedal.domain.payment.dto.request.CreatePaymentRequestDto;
import com.nameplz.baedal.domain.payment.dto.response.PaymentResponseDto;
import com.nameplz.baedal.domain.payment.service.PaymentService;
import com.nameplz.baedal.domain.store.domain.Store;
import com.nameplz.baedal.domain.user.domain.User;
import com.nameplz.baedal.global.common.exception.GlobalException;
import com.nameplz.baedal.global.common.response.ResultCase;
import com.nameplz.baedal.global.config.event.Events;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 주문을 처리하는 도메인 서비스
 * 도메인 서비스 : 도메인 영역에서 한 애그리거트에 넣기 복잡한 로직을 다루거나, 여러 애그리거트가 필요한 경우 사용
 * 또는 외부 서비스를 호출하는 로직을 처리할 때 사용 (이때는 인터페이스로 분리하여 구현체를 infra 패키지에 위치)
 */
@Service
public class OrderProcessService {

    public Order process(CreateOrderRequestDto requestDto, User user, Store store, PaymentService paymentService) {

        // 가게와 주문한 상품 리스트 가져오기
        List<UUID> productIdList = getOrderedProductIdList(requestDto);

        // 주문한 상품의 id가 가게에서 판매하는 상품인지 확인
        validateProductList(store, productIdList);

        // 결제 요청
        PaymentResponseDto paymentResponse = paymentService.createPayment(getCreatePaymentRequestDto(requestDto, user));

        // 결제 여부 검증
        validatePaymentResult(paymentResponse);

        // 주문 생성
        Order order = createOrderEntity(requestDto, user, store);
        addOrderLineListInOrder(requestDto, order);

        // 주문 생성 이벤트 발행
        raiseOrderCreatedEvent(user, store, order);

        return order;
    }

    private List<UUID> getOrderedProductIdList(CreateOrderRequestDto requestDto) {
        return requestDto.orderLineList()
                .stream()
                .map(CreateOrderLineRequestDto::productId)
                .toList();
    }

    private void validateProductList(Store store, List<UUID> productIdList) {
        if (!store.hasProductList(productIdList)) {
            throw new GlobalException(ResultCase.INVALID_INPUT);
        }
    }

    private CreatePaymentRequestDto getCreatePaymentRequestDto(CreateOrderRequestDto requestDto, User user) {
        PaymentRequestDto paymentInfo = requestDto.paymentInfo();
        return new CreatePaymentRequestDto(paymentInfo.amount(), paymentInfo.method(), user.getUsername());
    }

    private void validatePaymentResult(PaymentResponseDto paymentResponse) {
        if (!paymentResponse.status().equals(PaymentStatus.SUCCESS)) {
            throw new GlobalException(ResultCase.ORDER_FAILED);
        }
    }

    private Order createOrderEntity(CreateOrderRequestDto requestDto, User user, Store store) {

        return Order.create(
                OrderStatus.ACCEPT_WAITING,
                requestDto.orderType(),
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

    private void raiseOrderCreatedEvent(User user, Store store, Order order) {

        OrderCreatedEvent orderCreatedEvent = OrderCreatedEvent.of(
                order.getId(),
                user.getUsername(),
                store.getId(),
                order.getOrderStatus(),
                order.getOrderType(),
                order.getAddress(),
                order.getComment()
        );

        Events.raise(orderCreatedEvent);
    }
}
