package com.izzatalsharif.openai.autoblog.article.exception;

public class ArticleNotFoundException extends ArticleRequestException {

    public static final String MESSAGE = "article not found";

    public ArticleNotFoundException() {
        super(MESSAGE);
    }

    public ArticleNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }

}