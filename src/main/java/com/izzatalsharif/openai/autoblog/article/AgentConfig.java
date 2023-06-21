package com.izzatalsharif.openai.autoblog.article;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izzatalsharif.openai.autoblog.agent.AgentService;
import com.izzatalsharif.openai.autoblog.agent.OpenaiService;
import com.izzatalsharif.openai.autoblog.article.dto.agent.ArticleOutline;
import com.izzatalsharif.openai.autoblog.article.dto.agent.SectionExtraOutline;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.file.Files;

@RequiredArgsConstructor
@Configuration
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
    public AgentService<String, ArticleOutline> outlinerAgentService() throws IOException {
        var template = readFile("agent/outliner.json");
        return new AgentService<>(ArticleOutline.class, template, objectMapper, openaiService);
    }

    @Bean
    @Qualifier("writer")
    public AgentService<SectionExtraOutline, String> writerAgentService() throws IOException {
        var template = readFile("agent/writer.json");
        return new AgentService<>(String.class, template, objectMapper, openaiService);
    }

}
