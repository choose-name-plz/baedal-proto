package com.nameplz.baedal.domain.territory.service;

import com.nameplz.baedal.domain.territory.domain.Territory;
import com.nameplz.baedal.domain.territory.dto.response.TerritoryResponseDto;
import com.nameplz.baedal.domain.territory.mapper.TerritoryMapper;
import com.nameplz.baedal.domain.territory.repository.TerritoryRepository;
import com.nameplz.baedal.global.common.exception.GlobalException;
import com.nameplz.baedal.global.common.response.ResultCase;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TerritoryService {

    private final TerritoryRepository territoryRepository;
    private final TerritoryMapper territoryMapper;

    /**
     * territory 추가
     */
    @Transactional
    public String addTerritory(String name) {
        territoryRepository.findByName(name).ifPresent(error -> {
            throw new GlobalException(ResultCase.INVALID_INPUT);
        });

        Territory territory = Territory.createTerritory(name);
        territoryRepository.save(territory);
        return territory.getId().toString();
    }

    /**
     * territory 업데이트
     */
    @Transactional
    public TerritoryResponseDto updateTerritory(UUID territoryId, String territoryName) {
        Territory territory = findTerritoryById(territoryId);
        territory.updateTerritoryName(territoryName);
        return territoryMapper.categoryToResponseDto(territory);
    }

    /**
     * territory 삭제
     */
    @Transactional
    public String deleteTerritory(UUID territoryId, String username) {
        Territory territory = findTerritoryById(territoryId);
        territory.deleteEntity(username);
        return territory.getId().toString();
    }

    /**
     * territory 목록 조회
     */
    public List<TerritoryResponseDto> getTerritoryList(String name) {
        List<Territory> territoryList = territoryRepository.getCategoryList(name);
        return territoryMapper.listTerritoryToResponseDto(territoryList);
    }

    private Territory findTerritoryById(UUID territoryId) {
        return territoryRepository.findByIdAndDeletedAtIsNull(territoryId)
            .orElseThrow(() -> new GlobalException(ResultCase.TERRITORY_NOT_FOUND));
    }

}
