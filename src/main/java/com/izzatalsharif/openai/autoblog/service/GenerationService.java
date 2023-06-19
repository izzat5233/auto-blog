package com.izzatalsharif.openai.autoblog.service;

import com.izzatalsharif.openai.autoblog.dto.ArticleDTO;
import com.izzatalsharif.openai.autoblog.dto.SectionDTO;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenerationService {

    private final AgentService outlinerAgent;

    private final AgentService writerAgent;

    @Autowired
    public GenerationService(@Qualifier("outliner") AgentService outlinerAgent,
                             @Qualifier("writer") AgentService writerAgent) {
        this.outlinerAgent = outlinerAgent;
        this.writerAgent = writerAgent;
    }

    public ArticleDTO generateArticle(List<String> keywords) {
        var keys = keywords.toString();
        var articleOutline = outlinerAgent.request(keywords, ArticleOutline.class);
        var sections = new ArrayList<SectionDTO>();
        articleOutline.sections().forEach(sectionOutline -> {
            var writerPrompt = buildWriterPrompt(sectionOutline, keys);
            var content = writerAgent.request(writerPrompt, String.class);
            var sectionDTO = buildSectionDTO(sectionOutline, content);
            sections.add(sectionDTO);
        });
        return buildArticleDTO(articleOutline, sections, keys);
    }

    private ArticleDTO buildArticleDTO(ArticleOutline articleOutline, List<SectionDTO> sections, String keywords) {
        return ArticleDTO.builder()
                .title(articleOutline.title())
                .introduction(articleOutline.introduction())
                .keywords(keywords)
                .sections(sections)
                .conclusion(articleOutline.conclusion())
                .build();
    }

    private SectionDTO buildSectionDTO(SectionOutline sectionOutline, String content) {
        return SectionDTO.builder()
                .title(sectionOutline.title())
                .content(content)
                .build();
    }

    private WriterPrompt buildWriterPrompt(SectionOutline sectionOutline, String keywords) {
        return WriterPrompt.builder()
                .title(sectionOutline.title())
                .description(sectionOutline.description())
                .keywords(keywords)
                .subsections(sectionOutline.subsections())
                .build();
    }

    @Builder
    public record ArticleOutline(
            String title,
            String introduction,
            List<SectionOutline> sections,
            String conclusion
    ) {
    }

    @Builder
    public record SectionOutline(
            String title,
            String description,
            List<SectionOutline> subsections
    ) {
    }

    @Builder
    public record WriterPrompt(
            String keywords,
            String title,
            String description,
            List<SectionOutline> subsections
    ) {
    }

}
