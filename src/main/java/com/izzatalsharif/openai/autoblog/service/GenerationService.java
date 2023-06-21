package com.izzatalsharif.openai.autoblog.service;

import com.izzatalsharif.openai.autoblog.agent.AgentService;
import com.izzatalsharif.openai.autoblog.dto.ArticleDTO;
import com.izzatalsharif.openai.autoblog.dto.SectionDTO;
import com.izzatalsharif.openai.autoblog.dto.agent.ArticleOutline;
import com.izzatalsharif.openai.autoblog.dto.agent.SectionExtraOutline;
import com.izzatalsharif.openai.autoblog.mapper.ArticleMapper;
import com.izzatalsharif.openai.autoblog.mapper.SectionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class GenerationService {

    private final AgentService<String, ArticleOutline> outlinerAgent;

    private final AgentService<SectionExtraOutline, String> writerAgent;

    public ArticleDTO generateArticle(String keywords) {
        var articleOutline = outlinerAgent.requestAndParse(keywords);
        var sections = new ArrayList<SectionDTO>();
        articleOutline.sections().forEach(sectionOutline -> {
            var writerPrompt = SectionMapper.MAPPER.toWriterPrompt(sectionOutline, keywords);
            var content = writerAgent.requestAndParse(writerPrompt);
            var sectionDTO = SectionMapper.MAPPER.toSectionDTO(sectionOutline, content);
            sections.add(sectionDTO);
        });
        return ArticleMapper.MAPPER.toArticleDTO(articleOutline, sections, keywords);
    }

}
