package com.izzatalsharif.openai.autoblog.article.config;

import com.izzatalsharif.openai.autoblog.agent.AgentService;
import com.izzatalsharif.openai.autoblog.agent.AgentServiceFactory;
import com.izzatalsharif.openai.autoblog.article.dto.agent.ArticleOutline;
import com.izzatalsharif.openai.autoblog.article.dto.agent.SectionOutline;
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

    private final AgentServiceFactory agentServiceFactory;

    private String readFile(String resourcePath) throws IOException {
        var resource = resourceLoader.getResource("classpath:" + resourcePath);
        var path = resource.getFile().toPath();
        return Files.readString(path);
    }

    @Bean
    @Qualifier("outliner")
    public AgentService<String, ArticleOutline> outlinerAgentService() throws IOException {
        var template = readFile("agent/outliner.json");
        return agentServiceFactory.jsonFormatterAgentService(template, ArticleOutline.class);
    }

    @Bean
    @Qualifier("writer")
    public AgentService<SectionOutline, String> writerAgentService() throws IOException {
        var template = readFile("agent/writer.json");
        return agentServiceFactory.jsonFormatterAgentService(template, String.class);
    }

}
