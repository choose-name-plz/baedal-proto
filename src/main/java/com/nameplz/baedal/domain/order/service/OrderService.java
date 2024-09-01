package com.nameplz.baedal.domain.order.service;

import com.nameplz.baedal.domain.order.domain.Order;
import com.nameplz.baedal.domain.order.dto.request.CreateOrderRequestDto;
import com.nameplz.baedal.domain.order.dto.request.UpdateOrderStatusRequestDto;
import com.nameplz.baedal.domain.order.dto.response.OrderResponseDto;
import com.nameplz.baedal.domain.order.mapper.OrderMapper;
import com.nameplz.baedal.domain.order.repository.OrderRepository;
import com.nameplz.baedal.domain.payment.service.PaymentService;
import com.nameplz.baedal.domain.store.domain.Store;
import com.nameplz.baedal.domain.store.repository.StoreRepository;
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

/**
 * 주문 응용 서비스
 * 응용 서비스는 주로 도메인 객체 간 흐름 제어 및 트랜잭션 처리 등을 담당 (도메인 로직을 직접 구현하지 않음)
 * 도메인 로직은 도메인 객체 및 서비스에 위임하고, 응용 서비스는 도메인 객체 간의 협력을 조정 (Facade 패턴)
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderMapper mapper;
    private final OrderRepository orderRepository;
    private final OrderProcessService orderProcessService;
    private final StoreRepository storeRepository;
    private final PaymentService paymentService;

    /**
     * 주문 단건 조회
     */
    public OrderResponseDto getOrder(UUID orderId, User user) {
        Order order = findOrder(orderId);
        validateUserIsOrderer(order.getUser(), user);
        
        return mapper.toOrderResponseDto(order);
    }

    private void validateUserIsOrderer(User order, User user) {
        if (!order.getUsername().equals(user.getUsername())) {
            throw new GlobalException(ResultCase.INVALID_INPUT);
        }
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
    public List<OrderResponseDto> getOrderListByStoreId(UUID storeId, Pageable pageable, User user) {

        Store store = findStoreById(storeId);
        validateUserIsOwner(user, store);

        return orderRepository.findAllByStore_IdAndDeletedAtIsNull(storeId, pageable)
                .stream()
                .map(mapper::toOrderResponseDto)
                .toList();
    }

    private void validateUserIsOwner(User user, Store store) {
        if (!store.getUser().getUsername().equals(user.getUsername())) {
            throw new GlobalException(ResultCase.INVALID_INPUT);
        }
    }

    /**
     * 주문 생성
     */
    @Transactional
    public OrderResponseDto createOrder(CreateOrderRequestDto requestDto, User user) {

        // 가게 가져오기
        Store store = findStoreById(requestDto.storeId());

        // 주문 처리
        Order order = orderProcessService.process(requestDto, user, store, paymentService);

        // 주문 저장
        Order savedOrder = orderRepository.save(order);

        return mapper.toOrderResponseDto(savedOrder);
    }

    private Store findStoreById(UUID storeId) {

        return storeRepository.findByIdAndDeletedAtIsNull(storeId)
                .orElseThrow(() -> new GlobalException(ResultCase.STORE_NOT_FOUND));
    }

    /**
     * 주문 상태 변경
     */
    @Transactional
    public void updateOrderStatus(UUID orderId, UpdateOrderStatusRequestDto request) {

        Order order = findOrder(orderId);
        order.updateOrderStatus(request.orderStatus());
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
