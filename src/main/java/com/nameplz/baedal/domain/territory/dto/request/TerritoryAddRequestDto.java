package com.nameplz.baedal.domain.territory.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TerritoryAddRequestDto(
    @NotBlank(message = "지역 이름을 입력해주세요")
    String name) {

}
