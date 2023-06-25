package com.izzatalsharif.openai.autoblog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionDTO {

    @NotBlank(message = "Section must have a title")
    private String title;

    @NotBlank(message = "Section must have content")
    private String content;

}
