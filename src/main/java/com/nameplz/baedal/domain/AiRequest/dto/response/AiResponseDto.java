package com.nameplz.baedal.domain.AiRequest.dto.response;

/**
 * AI 상품 설명 요청 기록 조회시 return 할 정보
 *
 * @param username
 * @param requestContent
 * @param responseContent
 */
public record AiResponseDto(
    String username,
    String requestContent,
    String responseContent
) {

}

