package com.nameplz.baedal.domain.order.domain;

import com.nameplz.baedal.global.common.exception.GlobalException;
import com.nameplz.baedal.global.common.response.ResultCase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    PAYMENT_WAITING("결제 대기 중"),
    ACCEPT_WAITING("접수 대기 중"),
    COOKING("조리 중"),
    DELIVERING("배달 중"),
    DELIVERY_COMPLETED("배달 완료"),
    CANCELED("주문 취소됨");

    private final String description;

    public static OrderStatus findByName(String name) {

        return Arrays.stream(OrderStatus.values())
                .filter(orderStatus -> orderStatus.name().equals(name))
                .findAny()
                .orElseThrow(() -> new GlobalException(ResultCase.ORDER_STATUS_NOT_FOUND));
    }
}
