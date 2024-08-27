package com.nameplz.baedal.domain.territory.service;

import com.nameplz.baedal.domain.territory.domain.Territory;
import com.nameplz.baedal.domain.territory.dto.response.TerritoryResponseDto;
import com.nameplz.baedal.domain.territory.mapper.TerritoryMapper;
import com.nameplz.baedal.domain.territory.repository.TerritoryRepository;
import com.nameplz.baedal.global.common.exception.GlobalException;
import com.nameplz.baedal.global.common.response.ResultCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TerritoryService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final TerritoryRepository territoryRepository;
    private final TerritoryMapper territoryMapper;

    @Transactional
    public String addTerritory(String name) {
        territoryRepository.findByName(name).ifPresent(error -> {
            log.error("이미 존재하는 지역입니다 : {}", name);
            throw new GlobalException(ResultCase.INVALID_INPUT);
        });

        Territory territory = Territory.createTerritory(name);
        territoryRepository.save(territory);
        return territory.getId().toString();
    }

    public List<TerritoryResponseDto> getCategoryList(String name) {
        List<Territory> territoryList = territoryRepository.getCategoryList(name);
        return territoryMapper.territoryToResponseDto(territoryList);
    }

}
