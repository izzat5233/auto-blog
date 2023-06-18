package com.izzatalsharif.openai.autoblog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.izzatalsharif.openai.autoblog.dto.ArticleDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllArticles() throws Exception {
        var content = mvc.perform(get("/api/articles")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[*].title").isNotEmpty());
    }

    @Test
    void createArticle() throws Exception {
        var title = "Some Title";
        createArticle(title)
                .andExpect(status().isCreated());
        getArticle(title)
                .andExpect(jsonPath("$.title").value(title));
        createArticle(title)
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllTitles() throws Exception {
        mvc.perform(get("/api/articles/title")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[*]").isString());
    }

    @Test
    void getArticle() throws Exception {
        var title = "First Article";
        getArticle(title)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title));
    }

    @Test
    void deleteArticle() throws Exception {
        var title = "Second Article";
        getArticle(title)
                .andExpect(status().isOk());
        mvc.perform(delete("/api/articles/article" + title))
                .andExpect(status().isAccepted());
        getArticle(title)
                .andExpect(status().isNotFound());
        mvc.perform(delete("/api/articles/article" + title))
                .andExpect(status().isNotFound());
    }

    private String toJson(Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }

    private ResultActions getArticle(String title) throws Exception {
        return mvc.perform(get("/api/articles/article/" + title)
                .accept(MediaType.APPLICATION_JSON));
    }

    private ResultActions createArticle(String title) throws Exception {
        return null;
        /*return mvc.perform(post("/api/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new ArticleDTO(title, "Some Content"))));*/
    }

}
