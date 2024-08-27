package com.nameplz.baedal.domain.order.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderType {
    DELIVERY("배달"),
    TAKEOUT("포장"),
    EAT_IN("매장 식사");

    private final String description;
}
