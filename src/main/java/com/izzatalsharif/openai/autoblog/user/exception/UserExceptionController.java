package com.izzatalsharif.openai.autoblog.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionController {

    @ExceptionHandler(value = UserUnauthorizedException.class)
    public ResponseEntity<Object> handleUserUnauthorized(UserUnauthorizedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = UserRequestException.class)
    public ResponseEntity<Object> handleOtherExceptions(UserRequestException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
