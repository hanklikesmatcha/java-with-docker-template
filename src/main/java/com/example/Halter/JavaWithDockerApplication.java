package com.example.JavaWithDocker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class JavaWithDockerApplication {
    public static void main(String[] args) {
        SpringApplication.run(JavaWithDockerApplication.class, args);
    }
}
