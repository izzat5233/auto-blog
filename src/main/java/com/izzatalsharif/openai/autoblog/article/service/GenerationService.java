package com.izzatalsharif.openai.autoblog.article.service;

import com.izzatalsharif.openai.autoblog.agent.AgentService;
import com.izzatalsharif.openai.autoblog.article.dto.ArticleDTO;
import com.izzatalsharif.openai.autoblog.article.dto.SectionDTO;
import com.izzatalsharif.openai.autoblog.article.dto.agent.ArticleOutline;
import com.izzatalsharif.openai.autoblog.article.dto.agent.SectionOutline;
import com.izzatalsharif.openai.autoblog.article.mapper.ArticleMapper;
import com.izzatalsharif.openai.autoblog.article.mapper.SectionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GenerationService {

    private final AgentService<String, ArticleOutline> outlinerAgent;

    private final AgentService<SectionOutline, String> writerAgent;

    public Mono<ArticleDTO> generateArticle(String keywords) {
        return outlinerAgent.requestAndParse(keywords)
                .flatMap(articleOutline -> {
                    var monoList = generateSections(articleOutline);
                    return Flux.concat(monoList)
                            .collectList()
                            .map(sections -> ArticleMapper.MAPPER.toArticleDTO(articleOutline, sections, keywords));
                });
    }

    private List<Mono<SectionDTO>> generateSections(ArticleOutline articleOutline) {
        return articleOutline.sections().stream()
                .map(sectionOutline -> writerAgent.requestAndParse(sectionOutline)
                        .map(content -> SectionMapper.MAPPER.toSectionDTO(sectionOutline, content)))
                .collect(Collectors.toList());
    }

}
