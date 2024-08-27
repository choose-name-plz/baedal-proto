package com.nameplz.baedal.domain.order.domain;

import com.nameplz.baedal.global.common.exception.GlobalException;
import com.nameplz.baedal.global.common.response.ResultCase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum OrderType {

    ONLINE("온라인 주문"),
    OFFLINE("오프라인 주문");

    private final String description;

    public static OrderType findByName(String name) {

        return Arrays.stream(OrderType.values())
                .filter(orderType -> orderType.name().equals(name))
                .findAny()
                .orElseThrow(() -> new GlobalException(ResultCase.ORDER_TYPE_NOT_FOUND));
    }
}
