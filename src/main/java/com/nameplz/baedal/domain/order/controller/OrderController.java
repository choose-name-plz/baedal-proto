package com.nameplz.baedal.domain.order.controller;

import com.nameplz.baedal.domain.order.dto.request.CreateOrderRequestDto;
import com.nameplz.baedal.domain.order.dto.request.UpdateOrderStatusRequestDto;
import com.nameplz.baedal.domain.order.dto.response.OrderResponseDto;
import com.nameplz.baedal.domain.order.service.OrderService;
import com.nameplz.baedal.global.common.response.CommonResponse;
import com.nameplz.baedal.global.common.response.EmptyResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    /**
     * 주문 단건 조회
     */
    @GetMapping("/{orderId}")
    public CommonResponse<OrderResponseDto> getOrder(@PathVariable(name = "orderId") UUID orderId) {
        OrderResponseDto response = orderService.getOrder(orderId);

        return CommonResponse.success(response);
    }

    /**
     * 특정 사용자의 주문 목록 조회
     * Pageable 예시 : ?page=0&size=10&sort=createdAt,desc
     */
    @GetMapping("/users/{userId}")
    public CommonResponse<List<OrderResponseDto>> getOrderListByUsername(
            @PathVariable(name = "userId") String userId,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        log.info("pageable: {}", pageable);
        List<OrderResponseDto> response = orderService.getOrderListByUsername(userId, pageable);

        return CommonResponse.success(response);
    }

    /**
     * 특정 가게의 주문 목록 조회
     * Pageable 예시 : ?page=0&size=10&sort=createdAt,desc
     */
    @GetMapping("/stores/{storeId}")
    public CommonResponse<List<OrderResponseDto>> getOrderListByStoreId(
            @PathVariable(name = "storeId") UUID storeId,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        List<OrderResponseDto> response = orderService.getOrderListByStoreId(storeId, pageable);

        return CommonResponse.success(response);
    }

    /**
     * 주문 생성
     */
    @PostMapping
    public CommonResponse<OrderResponseDto> getOrderList(@Valid @RequestBody CreateOrderRequestDto request) {
        OrderResponseDto response = orderService.createOrder(request);

        return CommonResponse.success(response);
    }

    /**
     * 주문 상태 변경
     */
    @PatchMapping("/{orderId}/status")
    public CommonResponse<EmptyResponseDto> updateOrderStatus(
            @PathVariable UUID orderId,
            @RequestBody UpdateOrderStatusRequestDto request
    ) {
        orderService.updateOrderStatus(orderId, request);

        return CommonResponse.success();
    }

    /**
     * 주문 취소
     */
    @DeleteMapping("/{orderId}")
    public CommonResponse<EmptyResponseDto> cancelOrder(@PathVariable UUID orderId) {
        orderService.cancelOrder(orderId);

        return CommonResponse.success();
    }
}
