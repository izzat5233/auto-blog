package com.izzatalsharif.openai.autoblog.service;

import com.izzatalsharif.openai.autoblog.repository.ArticleRepository;
import com.izzatalsharif.openai.autoblog.dto.ArticleDTO;
import com.izzatalsharif.openai.autoblog.exception.ArticleNotFoundException;
import com.izzatalsharif.openai.autoblog.exception.ArticleRequestException;
import com.izzatalsharif.openai.autoblog.mapper.ArticleMapper;
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
