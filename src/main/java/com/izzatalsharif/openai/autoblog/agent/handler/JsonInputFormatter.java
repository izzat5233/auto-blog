package com.izzatalsharif.openai.autoblog.agent.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.izzatalsharif.openai.autoblog.agent.InputFormatter;
import com.izzatalsharif.openai.autoblog.agent.exception.OpenaiException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JsonInputFormatter<T>
        implements InputFormatter<T> {

    private final ObjectMapper objectMapper;

    @Override
    public String formatInput(T input) throws OpenaiException {
        try {
            var json = objectMapper.writeValueAsString(input);
            return quoteFix(json);
        } catch (JsonProcessingException e) {
            throw new OpenaiException("openai request couldn't parse to json");
        }
    }

    /**
     * Replaces all double quotes with single quotes.
     * This ensures all JSON parsers can parse it correctly.
     *
     * @param json the JSON string to be fixed
     * @return the fixed JSON string
     */
    private String quoteFix(String json) {
        return json.replaceAll("\"", "'");
    }

}
