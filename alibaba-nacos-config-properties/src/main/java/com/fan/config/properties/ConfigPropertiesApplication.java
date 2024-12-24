package com.fan.config.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ConfigPropertiesApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigPropertiesApplication.class, args);
    }


    @RefreshScope
    @Slf4j
    @RestController
    static class TestController{
//        didispace.title=spring-cloud-alibaba-learning
        @Value("${didispace.title:}")
        private String title;

        @GetMapping("/title")
        public String test() {
            return title;
        }
    }

}
