package com.nameplz.baedal.domain.store.dto.request;

public record ProductUpdateRequestDto(String name, String description, String image,
                                      boolean isPublic) {

}
