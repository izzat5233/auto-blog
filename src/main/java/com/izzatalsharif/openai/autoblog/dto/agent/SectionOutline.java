package com.izzatalsharif.openai.autoblog.dto.agent;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record SectionOutline(

        @NotBlank(message = "SectionOutline must have a title")
        String title,

        String description,

        List<SectionOutline> subsections

) {
}
