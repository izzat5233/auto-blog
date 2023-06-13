package com.izzatalsharif.openai.autoblog.exception;

public class UserUnauthorizedException extends UserRequestException {

    public static final String MESSAGE = "authorization required";

    public UserUnauthorizedException() {
        super(MESSAGE);
    }

    public UserUnauthorizedException(Throwable cause) {
        super(MESSAGE, cause);
    }

}
