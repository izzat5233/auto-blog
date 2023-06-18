package com.izzatalsharif.openai.autoblog.service;

import com.izzatalsharif.openai.autoblog.dto.ArticleDTO;
import com.izzatalsharif.openai.autoblog.exception.ArticleNotFoundException;
import com.izzatalsharif.openai.autoblog.exception.ArticleRequestException;
import com.izzatalsharif.openai.autoblog.mapper.ArticleMapper;
import com.izzatalsharif.openai.autoblog.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final ArticleMapper articleMapper;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    public List<ArticleDTO> getAllArticles() {
        return articleRepository.findAll()
                .stream().map(articleMapper.toDTO()).toList();
    }

    public List<String> getAllTitles() {
        return articleRepository.findAllTitles();
    }

    public ArticleDTO getArticle(String title) {
        return articleMapper.toDTO().apply(
                articleRepository.findByTitle(title)
                        .orElseThrow(ArticleNotFoundException::new)
        );
    }

    public void createArticle(ArticleDTO articleDTO) {
        var article = articleMapper.toEntity().apply(articleDTO);
        // make sure title is unique
        // todo: replace with a @Unique validator
        if (articleRepository.findByTitle(article.getTitle()).isPresent()) {
            throw new ArticleRequestException("title already used");
        }
        articleRepository.save(article);
    }

    public void deleteArticle(String title) {
        var article = articleRepository.findByTitle(title)
                .orElseThrow(ArticleNotFoundException::new);
        articleRepository.delete(article);
    }
}
