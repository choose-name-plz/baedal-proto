package com.nameplz.baedal.domain.store.dto.request;

import com.nameplz.baedal.domain.store.domain.StoreStatus;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record StoreUpdateRequestDto(
    @NotBlank(message = "가게 제목을 입력해주세요")
    String title,

    @NotBlank(message = "가게 설명을 입력해주세요")
    String description,

    @NotBlank(message = "가게 이미지를 입력해주세요")
    String image,

    StoreStatus status,

    UUID categoryId,
    UUID territoryId
) {

}
