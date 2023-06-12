package com.izzatalsharif.openai.autoblog.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;
import java.util.List;

@Configuration
public class ArticleConfig {

    @Bean
    @Autowired
    public CommandLineRunner initializeDatabase(ArticleRepository articleRepository) {
        return args -> articleRepository.saveAll(List.of(
                new Article(
                        1L,
                        "First Article",
                        "This is the content of the first article. It is quite interesting and covers a lot of ground.",
                        ZonedDateTime.now()
                ),
                new Article(
                        2L,
                        "Second Article",
                        "This is the content of the second article. It is even more interesting than the first one.",
                        ZonedDateTime.now().plusDays(1)
                ),
                new Article(
                        3L,
                        "Third Article",
                        "This is the content of the third article. It is the most interesting article yet.",
                        ZonedDateTime.now().plusDays(2)
                ),
                new Article(
                        4L,
                        "Fourth Article",
                        "This is the content of the fourth article. It is not as interesting as the third one, but still worth reading.",
                        ZonedDateTime.now().plusDays(3)
                ),
                new Article(
                        5L,
                        "Fifth Article",
                        "This is the content of the fifth article. It is the least interesting article so far, but it is still good.",
                        ZonedDateTime.now().plusDays(4)
                )
        ));
    }

}
