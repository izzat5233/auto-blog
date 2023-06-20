package com.izzatalsharif.openai.autoblog.dto;

import java.util.List;

public record SectionOutline(
        String title,
        String description,
        List<SectionOutline> subsections
) {
}
