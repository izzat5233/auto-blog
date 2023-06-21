package com.izzatalsharif.openai.autoblog.agent;

/**
 * The AgentService class is a key component in the interaction with the OpenAI API.
 * It is responsible for sending requests to the API and processing the responses.
 *
 * <p>Each AgentService instance is associated with a specific template, and data formatter.
 * The template is a JSON string that outlines the structure of the request to be sent to the OpenAI API.
 * Refer the OpenAI API documentation for how to formulate this JSON template.
 * The data formatter is a strategy that determines how the input and output data will be formatted.
 *
 * <p>The data formatter is designed to accommodate any formatting required by the prompt. The specific prompt used determines the appropriate data formatter.
 * This flexibility allows for a wide range of prompts and response formats to be used with the OpenAI API.
 *
 * <p>For example a prompt might ask ChatCompletion to accept XML and return MarkDown. In such case you would need
 * a DataFormatter to format object I into XML, and parse MarkDown into object O.
 *
 * <p>The requestAndParse method is the core method in this class. It accepts an input object of type I,
 * uses the data formatter to convert it to a string, and injects it into the request template. The resulting string is then sent as a request to the API.
 *
 * <p>The APIs response is a string. This string is processed by the data formatter to convert it to an object of the output class O
 * and returned by the requestAndParse method.
 *
 * <p>If an error occurs during the conversion, an OpenaiException is thrown. This can occur due to the inherent unpredictability of the OpenAI APIs responses.
 * Even if the logic of the application is sound, the API may return a response that cannot be successfully processed by the data formatter.
 *
 * <p>The request template must contain a placeholder for the prompt that will be included in the request.
 * The placeholder is specified in the template as "{prompt}".
 *
 * <p>For example, a template might look like this:
 * <pre>
 * {
 *   "model": "gpt-3.5-turbo",
 *   "messages": [
 *     {
 *       "role": "system",
 *       "content": "You are a helpful assistant."
 *     },
 *     {
 *       "role": "user",
 *       "content": "{prompt}"
 *     }
 *   ]
 * }
 * </pre>
 */
public class AgentService<I, O> {

    /**
     * The template string used for the OpenAI API request. This string should contain a "{prompt}" placeholder
     * that will be replaced with the actual prompt data.
     */
    private final String template;

    /**
     * The DataFormatter used to format the input data and parse the output data.
     */
    private final DataFormatter<I, O> formatter;

    /**
     * The OpenaiService used to send requests to the OpenAI API.
     */
    private final OpenaiService openaiService;

    /**
     * Constructs a new AgentService with the given template, formatter, and OpenaiService.
     *
     * @param template      the template string for the OpenAI API request
     * @param formatter     the DataFormatter for formatting and parsing data
     * @param openaiService the OpenaiService for sending requests to the OpenAI API
     */
    public AgentService(String template, DataFormatter<I, O> formatter, OpenaiService openaiService) {
        this.template = template;
        this.formatter = formatter;
        this.openaiService = openaiService;
    }

    /**
     * Sends a request to the OpenAI API with the given input data, and returns the parsed output data.
     *
     * @param input the input data
     * @return the parsed output data
     */
    public O requestAndParse(I input) {
        var response = request(input).getContent();
        return formatter.parseOutput(response);
    }

    /**
     * Sends a request to the OpenAI API with the given input data, and returns the raw Response.
     *
     * @param input the input data
     * @return the raw Response from the OpenAI API
     */
    public Response request(I input) {
        var prompt = formatter.formatInput(input);
        var body = injectRequest(prompt);
        return openaiService.chatCompletion(body);
    }

    /**
     * Injects a prompt into the request template.
     *
     * @param prompt the prompt to be injected into the request template
     * @return the request template with the prompt injected
     */
    private String injectRequest(String prompt) {
        prompt = fixPrompt(prompt);
        return template.replace("{prompt}", prompt);
    }

    /**
     * Replaces all double quotes in a string with single quotes.
     * This ensures any parser can parse this JSON string.
     *
     * @param prompt the string in which to replace double quotes
     * @return the string with all double quotes replaced with single quotes
     */
    private String fixPrompt(String prompt) {
        return prompt.replaceAll("\"", "'");
    }

}
