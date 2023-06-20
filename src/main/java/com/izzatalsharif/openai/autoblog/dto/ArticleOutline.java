package com.izzatalsharif.openai.autoblog.dto;

import java.util.List;

public record ArticleOutline(
        String title,
        String introduction,
        List<SectionOutline> sections,
        String conclusion
) {
}
