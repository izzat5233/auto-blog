package com.izzatalsharif.openai.autoblog.mapper;

import com.izzatalsharif.openai.autoblog.dto.ArticleDTO;
import com.izzatalsharif.openai.autoblog.model.Article;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ArticleMapper {

    private static final Function<Article, ArticleDTO> TO_DTO =
            article -> ArticleDTO.builder()
                    .title(article.getTitle())
                    .introduction(article.getIntroduction())
                    .build();

    private static final Function<ArticleDTO, Article> TO_ENTITY =
            articleDTO -> Article.builder()
                    .title(articleDTO.getTitle())
                    .introduction(articleDTO.getIntroduction())
                    .build();

    public Function<Article, ArticleDTO> toDTO() {
        return TO_DTO;
    }

    public Function<ArticleDTO, Article> toEntity() {
        return TO_ENTITY;
    }

}
