package com.izzatalsharif.openai.autoblog.controller;

import com.izzatalsharif.openai.autoblog.dto.ArticleDTO;
import com.izzatalsharif.openai.autoblog.service.ArticleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@Validated
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
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

}
