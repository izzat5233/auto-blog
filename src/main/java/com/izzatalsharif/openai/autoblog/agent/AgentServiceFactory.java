package com.izzatalsharif.openai.autoblog.agent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izzatalsharif.openai.autoblog.agent.formatter.JsonDataFormatter;
import com.izzatalsharif.openai.autoblog.agent.formatter.SimpleDataFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * The AgentServiceFactory class is responsible for creating instances of the AgentService class with the appropriate data formatter.
 *
 * <p>Each method in this factory corresponds to a specific type of data formatter:
 *
 * <ul>
 *   <li>The jsonFormatterAgentService method creates an AgentService instance that uses a JsonDataFormatter.
 *   This formatter uses the Jackson ObjectMapper to convert between Java objects and JSON. This method is
 *   suitable for cases where the input and output need to be formatted as JSON.</li>
 *   <li>The simpleAgentService method creates an AgentService instance that uses a simple string formatter
 *   (DataFormatter.STRING_DATA_FORMATTER). This formatter doesn't do any conversion and is suitable for
 *   cases where the input and output are already in the correct format.</li>
 * </ul>
 *
 * <p>The choice of data formatter depends on the specific requirements of the prompt that the AgentService will be used with.
 * The prompt determines the format of the input and output, and the appropriate data formatter should be chosen accordingly.
 *
 * <p>This design allows for flexibility and modularity. If a new type of data formatter is needed in the future,
 * a new method can be added to this factory to create AgentService instances with that formatter.
 */
@RequiredArgsConstructor
@Component
public class AgentServiceFactory {

    private final ObjectMapper objectMapper;

    private final OpenaiService openaiService;

    private final SimpleDataFormatter simpleDataFormatter;

    /**
     * Creates a new AgentService with the specified template and data formatter.
     *
     * @param template  the template to be used by the AgentService.
     *                  it must be a valid JSON string.
     * @param formatter the DataFormatter to be used by the AgentService
     * @return a new AgentService instance
     * @throws IllegalArgumentException if the template does not contain a prompt placeholder
     */
    public <I, O> AgentService<I, O> agentService(String template, DataFormatter<I, O> formatter)
            throws IllegalArgumentException {
        return new AgentService<>(template, formatter, openaiService);
    }

    /**
     * Creates a new AgentService with the specified template and a JsonDataFormatter.
     *
     * @param template    the template to be used by the AgentService
     * @param outputClass the class of the output object
     * @return a new AgentService instance
     * @throws IllegalArgumentException if the template does not contain a prompt placeholder
     */
    public <I, O> AgentService<I, O> jsonFormatterAgentService(String template, Class<O> outputClass)
            throws IllegalArgumentException {
        var formatter = new JsonDataFormatter<I, O>(objectMapper, outputClass);
        return agentService(template, formatter);
    }

    /**
     * Creates a new AgentService with the specified template and a SimpleDataFormatter.
     *
     * @param template the template to be used by the AgentService
     * @return a new AgentService instance
     * @throws IllegalArgumentException if the template does not contain a prompt placeholder
     */
    public AgentService<String, String> simpleAgentService(String template)
            throws IllegalArgumentException {
        return agentService(template, simpleDataFormatter);
    }

}
