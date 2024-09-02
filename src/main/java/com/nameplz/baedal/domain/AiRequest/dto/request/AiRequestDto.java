package com.nameplz.baedal.domain.AiRequest.dto.request;

import java.util.List;


/**
 * AI를 통한 상품 설명 자동생성시 받을 정보
 */

//    요청 데이터 양식
//    {
//        "contents":[
//            {
//            "parts":[
//                {
//                "text":"상품이름 추천해줘"
//                }]
//            }]
//    }

public record AiRequestDto(
    List<Content> contents
) {

    public record Content(
        List<Part> parts
    ) {

    }

    public record Part(
        String text
    ) {

    }
}
