package com.izzatalsharif.openai.autoblog.article.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {

    @NotBlank(message = "Article must have a title")
    @Size(max = 100, message = "Title must be less than 100 characters")
    private String title;

    @NotBlank(message = "Article must have keywords")
    private String keywords;

    @NotBlank(message = "Article must have an introduction")
    private String introduction;

    @NotEmpty(message = "Article must have sections")
    private List<SectionDTO> sections;

    @NotBlank(message = "Article must have a conclusion")
    private String conclusion;

}
