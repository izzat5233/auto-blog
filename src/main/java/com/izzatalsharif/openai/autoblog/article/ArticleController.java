package com.izzatalsharif.openai.autoblog.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("all")
    public List<Article> getAllArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping("all/titles")
    public List<String> getAllTitles() {
        return articleService.getAllTitles();
    }

    @GetMapping("article/{title}")
    public Article getArticle(@PathVariable("title") String title) {
        return articleService.getArticle(title);
    }
}
