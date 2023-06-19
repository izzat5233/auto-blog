package com.izzatalsharif.openai.autoblog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izzatalsharif.openai.autoblog.service.AgentService;
import com.izzatalsharif.openai.autoblog.service.OpenaiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.file.Files;

@Configuration
@RequiredArgsConstructor
public class AgentConfig {

    private final ResourceLoader resourceLoader;

    private final ObjectMapper objectMapper;

    private final OpenaiService openaiService;

    private String readFile(String resourcePath) throws IOException {
        var resource = resourceLoader.getResource("classpath:" + resourcePath);
        var path = resource.getFile().toPath();
        return Files.readString(path);
    }

    @Bean
    @Qualifier("outliner")
    public AgentService outlinerAgentService() throws IOException {
        var template = readFile("agent/outliner.json");
        return new AgentService(template, objectMapper, openaiService);
    }

    @Bean
    @Qualifier("writer")
    public AgentService writerAgentService() throws IOException {
        var template = readFile("agent/writer.json");
        return new AgentService(template, objectMapper, openaiService);
    }

}
