package com.nameplz.baedal.domain.store.dto.request;

import com.nameplz.baedal.domain.store.domain.StoreStatus;

public record StoreUpdateStatusRequestDto(
    StoreStatus status

) {

}
