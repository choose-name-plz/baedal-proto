package com.nameplz.baedal.domain.store.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ProductUpdateRequestDto(
    @NotBlank(message = "상품 이름일 입력해 주세요")
    String name,
    @NotBlank(message = "상품 설명을 입력해 주세요")
    String description,
    @NotBlank(message = "사진 주소를 입력해 주세요.")
    String image,
    boolean isPublic) {

}
