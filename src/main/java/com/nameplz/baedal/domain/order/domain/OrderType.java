package com.nameplz.baedal.domain.order.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderType {

    ONLINE("온라인 주문"),
    OFFLINE("오프라인 주문");

    private final String description;
}
