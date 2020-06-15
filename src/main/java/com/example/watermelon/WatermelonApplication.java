package com.example.watermelon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class WatermelonApplication {

    public static void main(String[] args) {
        SpringApplication.run(WatermelonApplication.class, args);
    }

    @GetMapping(path = "/hello")
    public String Hello() {
        return "Hello Docker World";
    }
}
