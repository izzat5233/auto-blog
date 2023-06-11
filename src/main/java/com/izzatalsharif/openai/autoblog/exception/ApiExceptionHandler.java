package com.izzatalsharif.openai.autoblog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        var status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(
                new ApiExceptionResponse(
                        e.getMessage(),
                        status,
                        ZonedDateTime.now(ZoneId.of("Z"))
                ),
                status
        );
    }

}
