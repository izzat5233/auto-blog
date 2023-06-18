package com.izzatalsharif.openai.autoblog.exception.controller;

import com.izzatalsharif.openai.autoblog.exception.ArticleNotFoundException;
import com.izzatalsharif.openai.autoblog.exception.ArticleRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ArticleExceptionController {

    @ExceptionHandler(value = ArticleNotFoundException.class)
    public ResponseEntity<Object> handleArticleNotFound(ArticleNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ArticleRequestException.class)
    public ResponseEntity<Object> handleOtherExceptions(ArticleRequestException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
