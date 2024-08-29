package com.nameplz.baedal.domain.store.dto.request;

import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.UUID;


public record ProductListCreateRequestDto(

    UUID storeId,

    @Size(min = 1, message = "최소 한개의 상품을 입력해주세요")
    List<ProductCreateRequestDto> productList
) {

}
