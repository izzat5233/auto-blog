package com.izzatalsharif.openai.autoblog.dto.agent;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ArticleOutline(

        @NotBlank(message = "ArticleOutline must have a title")
        String title,

        @NotBlank(message = "ArticleOutline must have an introduction")
        String introduction,

        @NotEmpty(message = "ArticleOutline must have sections")
        List<SectionOutline> sections,

        @NotBlank(message = "ArticleOutline must have a conclusion")
        String conclusion

) {
}
