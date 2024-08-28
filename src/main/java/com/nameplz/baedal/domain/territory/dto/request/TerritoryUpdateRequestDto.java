package com.nameplz.baedal.domain.territory.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TerritoryUpdateRequestDto(
    @NotBlank
    String name) {

}
