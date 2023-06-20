package com.izzatalsharif.openai.autoblog.service;

import com.izzatalsharif.openai.autoblog.dto.*;
import com.izzatalsharif.openai.autoblog.mapper.ArticleMapper;
import com.izzatalsharif.openai.autoblog.mapper.SectionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GenerationService {

    private final AgentService<List<String>, ArticleOutline> outlinerAgent;

    private final AgentService<WriterPrompt, String> writerAgent;

    public ArticleDTO generateArticle(List<String> keywords) {
        var keys = keywords.toString();
        var articleOutline = outlinerAgent.requestAndParse(keywords);
        var sections = new ArrayList<SectionDTO>();
        articleOutline.sections().forEach(sectionOutline -> {
            var writerPrompt = buildWriterPrompt(sectionOutline, keys);
            var content = writerAgent.requestAndParse(writerPrompt);
            var sectionDTO = SectionMapper.MAPPER.toSectionDTO(sectionOutline, content);
            sections.add(sectionDTO);
        });
        return ArticleMapper.MAPPER.toArticleDTO(articleOutline, sections, keys);
    }

    private WriterPrompt buildWriterPrompt(SectionOutline sectionOutline, String keywords) {
        return WriterPrompt.builder()
                .title(sectionOutline.title())
                .description(sectionOutline.description())
                .keywords(keywords)
                .subsections(sectionOutline.subsections())
                .build();
    }

}
