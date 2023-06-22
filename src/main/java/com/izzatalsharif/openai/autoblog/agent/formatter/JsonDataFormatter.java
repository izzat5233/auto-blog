package com.izzatalsharif.openai.autoblog.agent.formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.izzatalsharif.openai.autoblog.agent.DataFormatter;
import com.izzatalsharif.openai.autoblog.agent.exception.OpenaiException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JsonDataFormatter<I, O>
        implements DataFormatter<I, O> {

    private final ObjectMapper objectMapper;

    private final Class<O> outputClass;

    @Override
    public String formatInput(I input) {
        try {
            return jsonFix(objectMapper.writeValueAsString(input));
        } catch (JsonProcessingException e) {
            throw new OpenaiException("openai request couldn't parse");
        }
    }

    @Override
    public O parseOutput(String output) {
        try {
            return objectMapper.readValue(output, outputClass);
        } catch (JsonProcessingException e) {
            throw new OpenaiException("openai response couldn't parse");
        }
    }

    /**
     * Replaces all double quotes with single quotes.
     * This ensures all JSON parsers parse it correctly.
     *
     * @param json the JSON string to be fixed
     * @return the fixed JSON string
     */
    private String jsonFix(String json) {
        return json.replaceAll("\"", "'");
    }

}
