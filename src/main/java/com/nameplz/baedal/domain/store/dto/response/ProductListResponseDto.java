package com.nameplz.baedal.domain.store.dto.response;

import java.util.List;

public record ProductListResponseDto(
    List<ProductResponseDto> productList
) {

}
