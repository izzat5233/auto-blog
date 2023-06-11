package com.izzatalsharif.openai.autoblog.exception;

public class UserRequestException extends ApiRequestException {

    public UserRequestException(String message) {
        super(message);
    }

    public UserRequestException(String message, Throwable cause) {
        super(message, cause);
    }

}
