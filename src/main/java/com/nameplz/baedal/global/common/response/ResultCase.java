package com.nameplz.baedal.global.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResultCase {

    /* 성공 0번대 - 모든 성공 응답을 200으로 통일 */
    SUCCESS(HttpStatus.OK, 0, "정상 처리 되었습니다"),

    /* 글로벌 1000번대 */

    // 권한 없음 403
    NOT_AUTHORIZED(HttpStatus.FORBIDDEN, 1000, "해당 요청에 대한 권한이 없습니다."),
    // 잘못된 형식의 입력 400
    INVALID_INPUT(HttpStatus.BAD_REQUEST, 1001, "유효하지 않은 입력값입니다."),
    // 시스템 에러 500
    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 1002, "알 수 없는 에러가 발생했습니다."),

    /* 유저 2000번대 */

    // 존재하지 않는 사용자 404,
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 2000, "유저를 찾을 수 없습니다."),

    NOT_FOUND(HttpStatus.NOT_FOUND, 2002, "존재하지 않는 입력값입니다."),

    // 로그인 필요 401
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, 2001, "로그인이 필요합니다."),

    /* 주문 3000번대 */

    // 존재하지 않는 주문 타입 404
    ORDER_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, 3000, "주문 타입을 찾을 수 없습니다."),
    // 존재하지 않는 주문 상태 404
    ORDER_STATUS_NOT_FOUND(HttpStatus.NOT_FOUND, 3001, "주문 타입을 찾을 수 없습니다."),
    // 존재하지 않는 주문 404
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, 3002, "주문을 찾을 수 없습니다."),
    // 주문 취소 불가 400
    ORDER_CANCEL_DENIED(HttpStatus.BAD_REQUEST, 3003, "주문을 취소할 수 없습니다."),

    /* 결제 4000번대 */

    // 결제 정보를 찾을 수 없음 404
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, 4000, "결제 정보를 찾을 수 없습니다."),

    /* 리뷰 5000번대 */
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, 5000, "리뷰를 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus; // 응답 상태 코드
    private final Integer code; // 응답 코드. 도메인에 따라 1000번대로 나뉨
    private final String message; // 응답에 대한 설명
}
