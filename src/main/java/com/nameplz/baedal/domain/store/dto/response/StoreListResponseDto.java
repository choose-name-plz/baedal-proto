package com.nameplz.baedal.domain.store.dto.response;

import java.util.List;

public record StoreListResponseDto(int page, List<StoreResponseDto> storeList) {

}
