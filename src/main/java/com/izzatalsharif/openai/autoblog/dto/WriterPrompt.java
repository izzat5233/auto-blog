package com.izzatalsharif.openai.autoblog.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record WriterPrompt(
        String keywords,
        String title,
        String description,
        List<SectionOutline> subsections
) {
}
