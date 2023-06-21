package com.izzatalsharif.openai.autoblog.agent.exception;

public class OpenaiException extends RuntimeException {

    public OpenaiException(String message) {
        super(message);
    }

    public OpenaiException(String message, Throwable cause) {
        super(message, cause);
    }

}
