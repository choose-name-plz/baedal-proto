package com.nameplz.baedal.domain.territory.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TerritoryAddRequestDto(
    @NotBlank
    String name) {

}
