package com.example.slmiddleware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SLmiddlewareApplication {

    public static void main(String[] args) {
        SpringApplication.run(SLmiddlewareApplication.class, args);
    }

}
