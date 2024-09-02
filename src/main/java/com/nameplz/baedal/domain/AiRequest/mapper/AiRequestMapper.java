package com.nameplz.baedal.domain.AiRequest.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.nameplz.baedal.domain.AiRequest.domain.AiRequest;
import com.nameplz.baedal.domain.AiRequest.dto.response.AiResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = SPRING)
public interface AiRequestMapper {

    // entity To dto
    @Mapping(source = "user.username", target = "username")
    AiResponseDto aiRequestToAiResponseDto(AiRequest aiRequest);
}