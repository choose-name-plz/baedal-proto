package com.nameplz.baedal.global.common.exception;

import com.nameplz.baedal.global.common.response.CommonResponse;
import com.nameplz.baedal.global.common.response.EmptyResponseDto;
import com.nameplz.baedal.global.common.response.ResultCase;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 전역 예외 처리 핸들러
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final InvalidInputMapper mapper;
    private final HttpServletResponse response; // HttpStatus 설정을 위한 response 객체

    /**
     * Business 오류 발생에 대한 핸들러
     */
    @ExceptionHandler(GlobalException.class)
    public CommonResponse<EmptyResponseDto> handleGlobalException(GlobalException e) {
        response.setStatus(e.getResultCase().getHttpStatus().value()); // HttpStatus 설정

        return CommonResponse.error(e.getResultCase()); // 공통 응답 양식 반환
    }

    /**
     * Validation 라이브러리로 RequestBody 입력 파라미터 검증 오류 발생에 대한 핸들러
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse<List<InvalidInputResponseDto>> handlerValidationException(MethodArgumentNotValidException e) {
        // 잘못된 입력 에러들을 DTO 변환
        List<InvalidInputResponseDto> invalidInputResList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(mapper::toInvalidInputResponseDto) // defaultMessage 필드명을 message 변경
                .toList();

        response.setStatus(ResultCase.INVALID_INPUT.getHttpStatus().value()); // HttpStatus 설정

        return CommonResponse.error(ResultCase.INVALID_INPUT, invalidInputResList); // 공통 응답 양식 반환
    }
}
