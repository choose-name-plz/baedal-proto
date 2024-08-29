package com.nameplz.baedal.domain.store.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record ProductCreateRequestDto(
    @NotBlank(message = "상품 이름일 입력해 주세요")
    String name,
    @NotBlank(message = "상품 설명을 입력해 주세요")
    String description,
    @NotBlank(message = "사진 주소를 입력해 주세요.")
    String image,

    @Min(value = 10, message = "최소 가격은 10원입니다.")
    Integer price,

    UUID storeId
) {

}
