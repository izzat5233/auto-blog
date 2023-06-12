package com.izzatalsharif.openai.autoblog.exception.article;

public class ArticleRequestException extends RuntimeException {

    public ArticleRequestException(String message) {
        super(message);
    }

    public ArticleRequestException(String message, Throwable cause) {
        super(message, cause);
    }

}
