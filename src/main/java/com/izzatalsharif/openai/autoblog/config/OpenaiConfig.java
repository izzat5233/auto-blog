package com.izzatalsharif.openai.autoblog.config;

import com.izzatalsharif.openai.autoblog.model.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.nio.file.Files;

@Configuration
@RequiredArgsConstructor
public class OpenaiConfig {

    private static final String OPENAI_API_KEY =
            System.getenv("OPENAI_API_KEY");

    @Bean
    public WebClient chatCompletionWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + OPENAI_API_KEY)
                .build();
    }

    private final ResourceLoader resourceLoader;

    @Bean
    public RequestTemplate outlinerRequestTemplate() throws IOException {
        return new RequestTemplate(readFile("agent/outliner.json"));
    }

    @Bean
    public RequestTemplate writerRequestTemplate() throws IOException {
        return new RequestTemplate(readFile("agent/writer.json"));
    }

    private String readFile(String resourcePath) throws IOException {
        var resource = resourceLoader.getResource("classpath:" + resourcePath);
        var path = resource.getFile().toPath();
        return Files.readString(path);
    }

}
