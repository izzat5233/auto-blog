package com.izzatalsharif.openai.autoblog.mapper;

import com.izzatalsharif.openai.autoblog.dto.ArticleDTO;
import com.izzatalsharif.openai.autoblog.model.Article;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = SectionMapper.class)
public interface ArticleMapper {

    ArticleMapper MAPPER = Mappers.getMapper(ArticleMapper.class);

    Article toArticle(ArticleDTO articleDTO);

    ArticleDTO toArticleDTO(Article article);

}
