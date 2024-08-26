package com.nameplz.baedal.domain.territory.controller;

import com.nameplz.baedal.domain.territory.dto.request.TerritoryAddRequestDto;
import com.nameplz.baedal.domain.territory.dto.response.TerritoryResponseDto;
import com.nameplz.baedal.domain.territory.service.TerritoryService;
import com.nameplz.baedal.global.common.response.CommonResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("territory")
@RestController
public class TerritoryController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final TerritoryService territoryService;

    @PostMapping
    public ResponseEntity<CommonResponse<String>> addCategory(
        @RequestBody TerritoryAddRequestDto requestBody) {
        //TODO: 마스터만 호출할 수 있도록 추가
        String categoryId = territoryService.addTerritory(requestBody.name());
        return new ResponseEntity<>(CommonResponse.success(categoryId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<TerritoryResponseDto>>> getCategoryByName(
        @RequestParam(value = "name", required = false) String name
    ) {
        List<TerritoryResponseDto> category = territoryService.getCategoryList(name);
        return new ResponseEntity<>(CommonResponse.success(category), HttpStatus.OK);
    }

}
