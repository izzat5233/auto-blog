package com.izzatalsharif.openai.autoblog.service;

import com.izzatalsharif.openai.autoblog.model.Article;
import com.izzatalsharif.openai.autoblog.repository.ArticleRepository;
import com.izzatalsharif.openai.autoblog.exception.article.ArticleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public List<String> getAllTitles() {
        return articleRepository.findAllTitles();
    }

    public Article getArticle(String title) {
        return articleRepository.findByTitle(title)
                .orElseThrow(ArticleNotFoundException::new);
    }

}
