package com.izzatalsharif.openai.autoblog.dto;

import lombok.Builder;

@Builder
public record OpenaiRequest(
        String template,
        String prompt
) {

    private String promptFix() {
        return prompt.replaceAll("\"", "'");
    }

    @Override
    public String toString() {
        return template.replace("{prompt}", promptFix());
    }

}
