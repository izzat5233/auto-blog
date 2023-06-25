package com.izzatalsharif.openai.autoblog.config;

import com.izzatalsharif.openai.autoblog.dto.agent.ArticleOutline;
import com.izzatalsharif.openai.autoblog.dto.agent.SectionOutline;
import com.izzatalsharif.openai.chatagent.AgentService;
import com.izzatalsharif.openai.chatagent.AgentServiceFactory;
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

    /**
     * Creates a new AgentService bean that is used for outlining an article.
     * This bean is qualified with the name "outliner".
     *
     * <p>The template for the outliner agent is read from a JSON file.
     * The AgentService is created with a JsonInputFormatter and a JsonOutputParser,
     * which means that both the input and output are formatted as JSON.
     *
     * <p>The output class is ArticleOutline, which means that the output from the OpenAI API
     * is parsed into an ArticleOutline object.
     *
     * @return a new AgentService instance
     * @throws IOException if an error occurs while reading the template file
     */
    @Bean
    @Qualifier("outliner")
    public AgentService<String, ArticleOutline> outlinerAgentService() throws IOException {
        var template = readFile("agent/outliner.json");
        return agentServiceFactory.createJsonAgentService(template, ArticleOutline.class);
    }

    /**
     * Creates a new AgentService bean that is used for writing a section of an article.
     * This bean is qualified with the name "writer".
     *
     * <p>The template for the writer agent is read from a JSON file.
     * The AgentService is created with a JsonInputFormatter and a simple string output parser,
     * which means that the input is formatted as JSON and the output is returned as is.
     *
     * @return a new AgentService instance
     * @throws IOException if an error occurs while reading the template file
     */
    @Bean
    @Qualifier("writer")
    public AgentService<SectionOutline, String> writerAgentService() throws IOException {
        var template = readFile("agent/writer.json");
        return agentServiceFactory.createJsonFormatterAgentService(template, output -> output);
    }

}
