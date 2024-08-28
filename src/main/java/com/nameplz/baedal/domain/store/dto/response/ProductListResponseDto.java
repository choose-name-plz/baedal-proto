package com.nameplz.baedal.domain.store.dto.response;

import java.util.List;

public record ProductListResponseDto(
    int page,
    List<ProductResponseDto> productList

) {

}
