package com.izzatalsharif.openai.autoblog.agent;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AgentServiceFactory {

    private final ObjectMapper objectMapper;

    private final OpenaiService openaiService;

    public <I, O> AgentService<I, O> jsonFormatterAgentService(String template, Class<O> outputClass) {
        var formatter = new JsonDataFormatter<I, O>(objectMapper, outputClass);
        return new AgentService<>(template, formatter, openaiService);
    }

    public AgentService<String, String> simpleAgentService(String template) {
        return new AgentService<>(template, DataFormatter.STRING_DATA_FORMATTER, openaiService);
    }

}
