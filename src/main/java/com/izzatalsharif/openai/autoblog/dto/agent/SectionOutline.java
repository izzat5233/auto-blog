package com.izzatalsharif.openai.autoblog.dto.agent;

import java.util.List;

public record SectionOutline(
        String title,
        String description,
        List<SectionOutline> subsections
) {
}
