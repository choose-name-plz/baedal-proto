package com.nameplz.baedal.domain.AiRequest.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.nameplz.baedal.domain.AiRequest.domain.AiRequest;
import com.nameplz.baedal.domain.AiRequest.dto.response.AiResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = SPRING)
public interface AiRequestMapper {

    // entity To dto
    @Mappings({
        @Mapping(source = "user.username", target = "username"),
        @Mapping(source = "requestContent", target = "requestContent"),
        @Mapping(source = "responseContent", target = "responseContent")
    })
    AiResponseDto aiRequestToAiResponseDto(AiRequest aiRequest);
}