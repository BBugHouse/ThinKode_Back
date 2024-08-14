package com.bughouse.thinkode;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ThinkodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThinkodeApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println(">>> Server is running at " + ctx.getEnvironment().getProperty("local.server.port"));
        };
    }

}
