package com.nameplz.baedal.domain.store.dto.request;

import java.util.List;

public record ProductListCreateRequestDto(
    String storeId,
    List<ProductCreateRequestDto> productList
) {

}
