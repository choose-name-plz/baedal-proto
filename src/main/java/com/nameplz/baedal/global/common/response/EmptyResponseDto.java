package com.nameplz.baedal.global.common.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * data 필드가 필요 없는 경우 null 대신 비어있는 객체를 응답하기 위한 DTO
 */
@JsonIgnoreProperties // 아무 필드가 없을 시 직렬화를 위한 어노테이션
public record EmptyResponseDto() {
}
