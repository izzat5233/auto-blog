package com.izzatalsharif.openai.autoblog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izzatalsharif.openai.autoblog.dto.ArticleDTO;
import com.izzatalsharif.openai.autoblog.service.ArticleService;
import com.izzatalsharif.openai.autoblog.service.GenerationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
@Validated
public class ArticleController {

    private final ArticleService articleService;

    private final GenerationService generationService;

    @Autowired
    public ArticleController(ArticleService articleService, GenerationService generationService) {
        this.articleService = articleService;
        this.generationService = generationService;
    }

    @GetMapping("")
    public List<ArticleDTO> getAllArticles() {
        return articleService.getAllArticles();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createArticle(@Valid @RequestBody ArticleDTO article) {
        articleService.createArticle(article);
    }

    @GetMapping("/titles")
    public List<String> getAllTitles() {
        return articleService.getAllTitles();
    }

    @GetMapping("/article/{title}")
    public ArticleDTO getArticle(@NotBlank @PathVariable("title") String title) {
        return articleService.getArticle(title);
    }

    @DeleteMapping("/article/{title}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteArticle(@NotBlank @PathVariable("title") String title) {
        articleService.deleteArticle(title);
    }

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
