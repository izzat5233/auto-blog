package com.izzatalsharif.openai.autoblog.dto;

import java.util.List;

public record WriterPrompt(
        String keywords,
        String title,
        String description,
        List<SectionOutline> subsections
) {
}
