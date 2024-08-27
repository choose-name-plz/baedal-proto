package com.nameplz.baedal.domain.store.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreStatus {
    OPEN("오픈"),
    CLOSE("닫음"),
    PREPARING("준비중");

    private final String status;

}
