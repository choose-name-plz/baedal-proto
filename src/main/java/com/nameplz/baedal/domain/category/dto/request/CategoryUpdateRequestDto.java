package com.nameplz.baedal.domain.category.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryUpdateRequestDto(
    @NotBlank
    String name) {

}
