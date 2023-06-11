package com.izzatalsharif.openai.autoblog.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ApiExceptionResponse(
        String message,
        HttpStatus status,
        ZonedDateTime timestamp) {

}
