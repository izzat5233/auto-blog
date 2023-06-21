package com.izzatalsharif.openai.autoblog.article.exception;

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
