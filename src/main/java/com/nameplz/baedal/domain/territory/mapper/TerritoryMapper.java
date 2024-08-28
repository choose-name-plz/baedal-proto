package com.nameplz.baedal.domain.territory.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.nameplz.baedal.domain.territory.domain.Territory;
import com.nameplz.baedal.domain.territory.dto.response.TerritoryResponseDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface TerritoryMapper {

    // Territory목록을 ResponseDto로 매핑
    List<TerritoryResponseDto> listTerritoryToResponseDto(List<Territory> territoryList);

    TerritoryResponseDto categoryToResponseDto(Territory territory);
}
