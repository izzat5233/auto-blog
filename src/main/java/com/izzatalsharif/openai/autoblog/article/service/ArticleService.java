package com.izzatalsharif.openai.autoblog.article.service;

import com.izzatalsharif.openai.autoblog.article.ArticleRepository;
import com.izzatalsharif.openai.autoblog.article.dto.ArticleDTO;
import com.izzatalsharif.openai.autoblog.article.exception.ArticleNotFoundException;
import com.izzatalsharif.openai.autoblog.article.exception.ArticleRequestException;
import com.izzatalsharif.openai.autoblog.article.mapper.ArticleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<ArticleDTO> getAllArticles() {
        return articleRepository.findAll()
                .stream().map(ArticleMapper.MAPPER::toArticleDTO).toList();
    }

    public List<String> getAllTitles() {
        return articleRepository.findAllTitles();
    }

    public ArticleDTO getArticle(String title) {
        return ArticleMapper.MAPPER.toArticleDTO(
                articleRepository.findByTitle(title)
                        .orElseThrow(ArticleNotFoundException::new));
    }

    public ArticleDTO createArticle(ArticleDTO articleDTO) {
        var article = ArticleMapper.MAPPER.toArticle(articleDTO);
        // make sure title is unique
        // todo: replace with a @Unique validator
        if (articleRepository.findByTitle(article.getTitle()).isPresent()) {
            throw new ArticleRequestException("title already used");
        }
        article = articleRepository.save(article);
        return ArticleMapper.MAPPER.toArticleDTO(article);
    }

    public void deleteArticle(String title) {
        var article = articleRepository.findByTitle(title)
                .orElseThrow(ArticleNotFoundException::new);
        articleRepository.delete(article);
    }
}
