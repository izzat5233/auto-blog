package com.izzatalsharif.openai.autoblog.mapper;

import com.izzatalsharif.openai.autoblog.dto.ArticleDTO;
import com.izzatalsharif.openai.autoblog.dto.ArticleOutline;
import com.izzatalsharif.openai.autoblog.dto.SectionDTO;
import com.izzatalsharif.openai.autoblog.model.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = SectionMapper.class)
public interface ArticleMapper {

    ArticleMapper MAPPER = Mappers.getMapper(ArticleMapper.class);

    Article toArticle(ArticleDTO articleDTO);

    ArticleDTO toArticleDTO(Article article);

    @Mapping(target = ".", source = "articleOutline")
    @Mapping(target = "keywords", source = "keywords")
    @Mapping(target = "sections", source = "sections")
    ArticleDTO toArticleDTO(ArticleOutline articleOutline,
                            List<SectionDTO> sections,
                            String keywords);

}
