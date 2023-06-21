package com.izzatalsharif.openai.autoblog.article.exception;

public class ArticleRequestException extends RuntimeException {

    public ArticleRequestException(String message) {
        super(message);
    }

    public ArticleRequestException(String message, Throwable cause) {
        super(message, cause);
    }

}
