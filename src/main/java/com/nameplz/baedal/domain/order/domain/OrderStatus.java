package com.nameplz.baedal.domain.order.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
}
