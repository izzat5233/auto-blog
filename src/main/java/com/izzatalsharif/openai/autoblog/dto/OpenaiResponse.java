package com.izzatalsharif.openai.autoblog.dto;

import java.util.List;

public record OpenaiResponse(
        String id,
        String object,
        String model,
        Usage usage,
        List<Choice> choices
) {

    public record Usage(
            Integer prompt_tokens,
            Integer completion_tokens,
            Integer total_tokens
    ) {
    }

    public record Choice(
            Message message,
            String finish_reason,
            Integer index
    ) {

        public record Message(
                String role,
                String content
        ) {
        }

    }

    public String getResponse() {
        return choices.get(0).message.content;
    }

}
