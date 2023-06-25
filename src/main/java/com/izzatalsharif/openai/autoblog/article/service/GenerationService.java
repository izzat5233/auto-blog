package com.izzatalsharif.openai.autoblog.article.service;

import com.izzatalsharif.openai.autoblog.article.dto.ArticleDTO;
import com.izzatalsharif.openai.autoblog.article.dto.SectionDTO;
import com.izzatalsharif.openai.autoblog.article.dto.agent.ArticleOutline;
import com.izzatalsharif.openai.autoblog.article.dto.agent.SectionOutline;
import com.izzatalsharif.openai.autoblog.article.mapper.ArticleMapper;
import com.izzatalsharif.openai.autoblog.article.mapper.SectionMapper;
import com.izzatalsharif.openai.chatagent.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenerationService {

    private final AgentService<String, ArticleOutline> outlinerAgent;

    private final AgentService<SectionOutline, String> writerAgent;

    public Mono<ArticleDTO> generateArticle(String keywords) {
        return outlinerAgent.requestAndParse(keywords)
                .flatMap(articleOutline -> generateSections(articleOutline)
                        .map(sections -> ArticleMapper.MAPPER.toArticleDTO(articleOutline, sections, keywords)));
    }

    private Mono<List<SectionDTO>> generateSections(ArticleOutline articleOutline) {
        return Flux.fromIterable(articleOutline.sections())
                .flatMap(sectionOutline -> writerAgent.requestAndParse(sectionOutline)
                                .map(content -> SectionMapper.MAPPER.toSectionDTO(sectionOutline, content)),
                        articleOutline.sections().size())
                .collectList();
    }

}
