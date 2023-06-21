package com.izzatalsharif.openai.autoblog.article.dto.agent;

import java.util.List;

public record SectionOutline(
        String title,
        String description,
        List<SectionOutline> subsections
) {
}
