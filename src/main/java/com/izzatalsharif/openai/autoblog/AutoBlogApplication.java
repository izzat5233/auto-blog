package com.izzatalsharif.openai.autoblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AutoBlogApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(AutoBlogApplication.class, args);
        System.out.println(context.getBean("restTemplate").getClass());
    }

}
