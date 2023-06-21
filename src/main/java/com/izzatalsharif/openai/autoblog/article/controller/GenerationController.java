package com.izzatalsharif.openai.autoblog.article.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izzatalsharif.openai.autoblog.article.dto.ArticleDTO;
import com.izzatalsharif.openai.autoblog.article.service.GenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/generate")
public class GenerationController {

    private final GenerationService generationService;

    @GetMapping("/generate")
    @ResponseStatus(HttpStatus.CREATED)
    public void generateArticle() throws IOException {
        ArticleDTO articleDTO = generationService.generateArticle("Bitcoin ElonMusk");
        System.out.println(articleDTO);
        String json = new ObjectMapper().writeValueAsString(articleDTO);
        System.out.println(json);
        writeFile(json);
    }

    public void writeFile(String content) throws IOException {
        Files.writeString(Path.of("response"), content, StandardOpenOption.CREATE);
    }

}
