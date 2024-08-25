package com.nameplz.baedal.global.error;

// 커스텀 예외 클래스 등록
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
