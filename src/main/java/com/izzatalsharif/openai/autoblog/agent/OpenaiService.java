package com.izzatalsharif.openai.autoblog.agent;

import com.izzatalsharif.openai.autoblog.agent.exception.OpenaiException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
class OpenaiService {

    private final WebClient webClient;

    public OpenaiService(@Qualifier("chatCompletionWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Response chatCompletion(String requestBody) {
        var responseMono = webClient.post()
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new OpenaiException("4xx error " + response.statusCode())))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new OpenaiException("5xx error " + response.statusCode())))
                .bodyToMono(Response.class);
        return responseMono.block();
    }

}
