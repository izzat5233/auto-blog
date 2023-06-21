package com.izzatalsharif.openai.autoblog.agent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.izzatalsharif.openai.autoblog.agent.exception.OpenaiException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AgentService<I, O> {

    @Getter
    private final String template;

    private final Class<O> outputClass;

    private final ObjectMapper objectMapper;

    private final OpenaiService openaiService;

    public O requestAndParse(I input) {
        var response = request(input).getContent();
        return fromJson(response, outputClass);
    }

    public Response request(I input) {
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
