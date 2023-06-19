package com.izzatalsharif.openai.autoblog.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.izzatalsharif.openai.autoblog.dto.ResponseDTO;
import com.izzatalsharif.openai.autoblog.exception.OpenaiException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AgentService {

    private final String template;

    private final ObjectMapper objectMapper;

    private final OpenaiService openaiService;

    public <T> T request(Object input, Class<T> outputClass) {
        var response = request(input).getContent();
        return fromJson(response, outputClass);
    }

    public ResponseDTO request(Object input) {
        var prompt = toJson(input);
        var body = injectRequest(prompt);
        return openaiService.chatCompletion(body);
    }

    private String toJson(Object data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new OpenaiException("openai request couldn't parse");
        }
    }

    private <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new OpenaiException("openai response couldn't parse");
        }
    }

    private String injectRequest(String prompt) {
        prompt = fixPrompt(prompt);
        return template.replace("{prompt}", prompt);
    }

    private String fixPrompt(String prompt) {
        return prompt.replaceAll("\"", "'");
    }

}
