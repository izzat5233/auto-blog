package com.izzatalsharif.openai.autoblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class
})
@ComponentScan(basePackages = {
        "com.izzatalsharif.openai.autoblog",
        "com.izzatalsharif.openai.chatagent"
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
