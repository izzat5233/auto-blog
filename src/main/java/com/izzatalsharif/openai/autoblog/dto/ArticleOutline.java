package com.izzatalsharif.openai.autoblog.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ArticleOutline(
        String title,
        String introduction,
        List<SectionOutline> sections,
        String conclusion
) {
}
