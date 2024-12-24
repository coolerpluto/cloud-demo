package com.fan.discovery.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.Name;

@EnableDiscoveryClient
@SpringBootApplication
public class DiscoveryServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerApplication.class, args);
    }

    @Slf4j
    @RestController
    static class TestController{

        @GetMapping("/hello")
        public String Hello(@RequestParam("name") String name) {
            log.info("invoked name = " + name);
            return "hello:" + name;
        }
    }
}