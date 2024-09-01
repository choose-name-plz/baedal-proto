package com.nameplz.baedal.domain.AiRequest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nameplz.baedal.domain.AiRequest.dto.request.AiRequestDto;
import com.nameplz.baedal.domain.AiRequest.dto.response.AiResponseDto;
import com.nameplz.baedal.domain.AiRequest.service.AiRequestService;
import com.nameplz.baedal.domain.user.domain.UserRole.Authority;
import com.nameplz.baedal.global.common.response.CommonResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/ai-request")
@RequiredArgsConstructor
public class AiRequestController {

    private final AiRequestService aiRequestService;

    /**
     * AI를 통한 상품 설명 자동생성
     */
    @Secured({Authority.MASTER, Authority.OWNER})
    @PostMapping
    public CommonResponse<AiResponseDto> createAiRequest(
        @RequestBody AiRequestDto aiRequest, @RequestParam String username)
        throws JsonProcessingException {
        AiResponseDto aiRequestDto = aiRequestService.createAiRequest(aiRequest, username);
        return CommonResponse.success(aiRequestDto);
    }

    /**
     * AI 상품 설명 요청 기록 조회
     */
    @Secured({Authority.MASTER, Authority.OWNER})
    @GetMapping("/{username}")
    public CommonResponse<List<AiResponseDto>> getAiRequest(@PathVariable String username) {
        List<AiResponseDto> aiRequestDto = aiRequestService.getAiRequest(username);
        return CommonResponse.success(aiRequestDto);
    }

}
