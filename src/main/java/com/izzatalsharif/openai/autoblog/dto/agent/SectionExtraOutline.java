package com.izzatalsharif.openai.autoblog.dto.agent;

import java.util.List;

public record SectionExtraOutline(
        String keywords,
        String title,
        String description,
        List<SectionOutline> subsections
) {
}
