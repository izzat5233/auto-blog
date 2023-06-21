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
            return objectMapper.writeValueAsString(input);
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

}
