package com.izzatalsharif.openai.autoblog.agent;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AgentServiceFactory {

    private final ObjectMapper objectMapper;

    private final OpenaiService openaiService;

    public <I, O> AgentService<I, O> create(String template, Class<O> outputClass) {
        return new AgentService<>(template, outputClass, objectMapper, openaiService);
    }

}
