package com.izzatalsharif.openai.autoblog.dto.agent;

import java.util.List;

public record ArticleOutline(
        String title,
        String introduction,
        List<SectionOutline> sections,
        String conclusion
) {
}
