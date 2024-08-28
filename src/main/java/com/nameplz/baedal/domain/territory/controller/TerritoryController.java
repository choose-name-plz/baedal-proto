package com.nameplz.baedal.domain.territory.controller;

import com.nameplz.baedal.domain.territory.dto.request.TerritoryAddRequestDto;
import com.nameplz.baedal.domain.territory.dto.request.TerritoryUpdateRequestDto;
import com.nameplz.baedal.domain.territory.dto.response.TerritoryIdResponseDto;
import com.nameplz.baedal.domain.territory.dto.response.TerritoryResponseDto;
import com.nameplz.baedal.domain.territory.service.TerritoryService;
import com.nameplz.baedal.global.common.response.CommonResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("territory")
@RestController
public class TerritoryController {

    private final TerritoryService territoryService;

    /**
     * territory 생성
     */
    @PostMapping
    public ResponseEntity<CommonResponse<TerritoryIdResponseDto>> addCategory(
        @RequestBody @Validated TerritoryAddRequestDto requestBody) {
        //TODO: 마스터만 호출할 수 있도록 추가
        String territoryId = territoryService.addTerritory(requestBody.name());
        return new ResponseEntity<>(
            CommonResponse.success(new TerritoryIdResponseDto(territoryId)),
            HttpStatus.CREATED);
    }

    /**
     * territory 목록 조회
     */
    @GetMapping
    public CommonResponse<List<TerritoryResponseDto>> getCategoryByName(
        @RequestParam(value = "name", required = false) String name
    ) {
        List<TerritoryResponseDto> category = territoryService.getTerritoryList(name);
        return CommonResponse.success(category);
    }

    /**
     * territory 업데이트
     */
    @PutMapping("/{id}")
    public CommonResponse<TerritoryResponseDto> updateTerritory(
        @PathVariable("id") UUID territoryId,
        @RequestBody @Validated TerritoryUpdateRequestDto requestDto
    ) {

        //TODO: 마스터만 호출할 수 있도록 추가
        TerritoryResponseDto territoryResponseDto = territoryService.updateTerritory(
            territoryId, requestDto.name());

        return CommonResponse.success(territoryResponseDto);
    }


    /**
     * territory 삭제
     */
    @DeleteMapping("/{id}")
    public CommonResponse<TerritoryIdResponseDto> deleteTerritory(
        @PathVariable("id") UUID territoryId
    ) {
        //TODO: 마스터만 호출할 수 있도록 추가
        String username = "iron";
        String deletedTerritoryId = territoryService.deleteTerritory(territoryId, username);

        return CommonResponse.success(new TerritoryIdResponseDto(deletedTerritoryId));
    }

}
