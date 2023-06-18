package com.izzatalsharif.openai.autoblog.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.izzatalsharif.openai.autoblog.dto.*;
import com.izzatalsharif.openai.autoblog.exception.OpenaiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenerationService {

    private final WebClient webClient;

    private final String outlinerTemplate;

    private final String writerTemplate;

    private final ObjectMapper objectMapper;

    @Autowired
    public GenerationService(@Qualifier("chatCompletionWebClient") WebClient webClient,
                             @Qualifier("outlinerRequestTemplate") String outlinerTemplate,
                             @Qualifier("writerRequestTemplate") String writerTemplate,
                             ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.outlinerTemplate = outlinerTemplate;
        this.writerTemplate = writerTemplate;
        this.objectMapper = objectMapper;
    }

    public ArticleDTO generateArticle(List<String> keywords) {
        var keys = keywords.toString();
        var articleOutline = generateArticleOutline(keys);
        var sections = new ArrayList<SectionDTO>();
        articleOutline.sections().forEach(sectionOutline -> {
            var writerPrompt = buildWriterPrompt(sectionOutline, keys);
            var content = requestWriter(writerPrompt).getResponse();
            var sectionDTO = buildSectionDTO(sectionOutline, content);
            sections.add(sectionDTO);
        });
        return buildArticleDTO(articleOutline, sections, keys);
    }

    private ArticleDTO buildArticleDTO(ArticleOutline articleOutline, List<SectionDTO> sections, String keywords) {
        return ArticleDTO.builder()
                .title(articleOutline.title())
                .introduction(articleOutline.introduction())
                .keywords(keywords)
                .sections(sections)
                .conclusion(articleOutline.conclusion())
                .build();
    }

    private SectionDTO buildSectionDTO(SectionOutline sectionOutline, String content) {
        return SectionDTO.builder()
                .title(sectionOutline.title())
                .content(content)
                .build();
    }

    private WriterPrompt buildWriterPrompt(SectionOutline sectionOutline, String keywords) {
        return WriterPrompt.builder()
                .title(sectionOutline.title())
                .description(sectionOutline.description())
                .keywords(keywords)
                .subsections(sectionOutline.subsections())
                .build();
    }

    private ArticleOutline generateArticleOutline(String keywords) {
        var response = requestOutliner(keywords);
        try {
            return objectMapper.readValue(response.getResponse(), ArticleOutline.class);
        } catch (JsonProcessingException e) {
            throw new OpenaiException("bad response format");
        }
    }

    private OpenaiResponse requestOutliner(String keywords) {
        return chatCompletion(OpenaiRequest.builder()
                .template(outlinerTemplate)
                .prompt(keywords)
                .build());
    }

    private OpenaiResponse requestWriter(WriterPrompt prompt) {
        try {
            return chatCompletion(OpenaiRequest.builder()
                    .template(writerTemplate)
                    .prompt(objectMapper.writeValueAsString(prompt))
                    .build());
        } catch (JsonProcessingException e) {
            throw new OpenaiException("json parsing failed");
        }
    }

    private OpenaiResponse chatCompletion(OpenaiRequest request) {
        System.out.println(request);
        var responseMono = webClient.post()
                .body(BodyInserters.fromValue(request.toString()))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new OpenaiException("4xx error " + response.statusCode())))
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new OpenaiException("5xx error " + response.statusCode())))
                .bodyToMono(OpenaiResponse.class);
        var response = responseMono.block();
        System.out.println(response);
        return response;
            /*responseMono.subscribe(response -> System.out.println(response),
                    error -> System.err.println("Error: " + error.getMessage()));*/
    }

}
