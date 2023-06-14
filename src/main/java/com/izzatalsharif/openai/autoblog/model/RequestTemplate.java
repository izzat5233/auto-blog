package com.izzatalsharif.openai.autoblog.model;

public record RequestTemplate(
        String body
) {

    public String inject(String prompt) {
        return body.replace("{prompt}", prompt);
    }

}
