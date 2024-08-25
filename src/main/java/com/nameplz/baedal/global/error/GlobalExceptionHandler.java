package com.nameplz.baedal.global.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // 모든 컨트롤러에서 발생하는 예외를 처리하는 글로벌 예외 처리기
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class) // 특정 예외가 발생했을 때 호출될 메서드를 지정
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) { // 이 메소드를
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()); // 상태코드와 상태메세지를 리턴
    }

    // ... 다른 예외 핸들러들 작성가능
}
