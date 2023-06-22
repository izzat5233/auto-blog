package com.izzatalsharif.openai.autoblog.article.service;

import com.izzatalsharif.openai.autoblog.agent.AgentService;
import com.izzatalsharif.openai.autoblog.article.dto.ArticleDTO;
import com.izzatalsharif.openai.autoblog.article.dto.agent.ArticleOutline;
import com.izzatalsharif.openai.autoblog.article.dto.agent.SectionExtraOutline;
import com.izzatalsharif.openai.autoblog.article.mapper.ArticleMapper;
import com.izzatalsharif.openai.autoblog.article.mapper.SectionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GenerationService {

    private final AgentService<String, ArticleOutline> outlinerAgent;

    private final AgentService<SectionExtraOutline, String> writerAgent;

    public Mono<ArticleDTO> generateArticle(String keywords) {
        return outlinerAgent.requestAndParse(keywords)
                .flatMap(articleOutline -> {
                    var sectionDTOs = articleOutline.sections().stream()
                            .map(sectionOutline -> {
                                var request = SectionMapper.MAPPER.toSectionExtraOutline(sectionOutline, keywords);
                                return generateSection(request)
                                        .map(content -> SectionMapper.MAPPER.toSectionDTO(sectionOutline, content));
                            })
                            .collect(Collectors.toList());
                    return Flux.concat(sectionDTOs)
                            .collectList()
                            .map(sections -> ArticleMapper.MAPPER.toArticleDTO(articleOutline, sections, keywords));
                });
    }

    public Mono<String> generateSection(SectionExtraOutline sectionExtraOutline) {
        return writerAgent.requestAndParse(sectionExtraOutline);
    }

}
