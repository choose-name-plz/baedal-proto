package com.nameplz.baedal.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> implements Serializable {

    private Integer code; // 커스텀 응답 코드
    private String message; // 응답에 대한 설명
    private T data; // 응답에 필요한 데이터

    /**
     * data 필드에 값을 넣을 때 사용하는 메서드 - data 필드가 필요 없는 경우
     */
    public static CommonResponse<EmptyResponseDto> success() {
        return CommonResponse.<EmptyResponseDto>builder()
                .code(ResultCase.SUCCESS.getCode())
                .message(ResultCase.SUCCESS.getMessage())
                .data(new EmptyResponseDto())
                .build();
    }

    /**
     * data 필드에 값을 넣을 때 사용하는 메서드 - data 필드가 필요한 경우
     */
    public static <T> CommonResponse<T> success(T data) {
        return CommonResponse.<T>builder()
                .code(ResultCase.SUCCESS.getCode())
                .message(ResultCase.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    /**
     * 에러 발생 시 특정 에러에 맞는 응답하는 메서드 - data 필드가 필요 없는 경우
     */
    public static CommonResponse<EmptyResponseDto> error(ResultCase resultCase) {

        return CommonResponse.<EmptyResponseDto>builder()
                .code(resultCase.getCode())
                .message(resultCase.getMessage())
                .data(new EmptyResponseDto())
                .build();
    }

    /**
     * 에러 발생 시 특정 에러에 맞는 응답하는 메서드 - data 필드가 필요한 경우
     */
    public static <T> CommonResponse<T> error(ResultCase resultCase, T data) {
        return CommonResponse.<T>builder()
                .code(resultCase.getCode())
                .message(resultCase.getMessage())
                .data(data)
                .build();
    }
}
