package com.izzatalsharif.openai.autoblog.exception.controller;

import com.izzatalsharif.openai.autoblog.exception.OpenaiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OpenaiExceptionController {

    @ExceptionHandler(value = OpenaiException.class)
    public ResponseEntity<Object> handleOtherExceptions(OpenaiException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
