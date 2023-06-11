package com.izzatalsharif.openai.autoblog.exception;

public class ArticleRequestException extends ApiRequestException {

    public ArticleRequestException(String message) {
        super(message);
    }

    public ArticleRequestException(String message, Throwable cause) {
        super(message, cause);
    }

}
