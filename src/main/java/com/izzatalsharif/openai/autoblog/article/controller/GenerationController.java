package com.izzatalsharif.openai.autoblog.article.controller;

import com.izzatalsharif.openai.autoblog.article.dto.ArticleDTO;
import com.izzatalsharif.openai.autoblog.article.service.ArticleService;
import com.izzatalsharif.openai.autoblog.article.service.GenerationService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/generate")
public class GenerationController {

    private final GenerationService generationService;

    private final ArticleService articleService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ArticleDTO> generateArticle(@NotBlank @RequestBody String keywords) {
        return generationService.generateArticle(keywords)
                .map(articleService::createArticle);
    }

}
