package com.fan.discovery.client.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
public class ClientCommonApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientCommonApplication.class, args);
    }

    @RestController
    @Slf4j
    static class TestController{

        //需添加依赖spring-cloud-starter-loadbalancer
        @Autowired
        LoadBalancerClient loadBalancerClient;

        @GetMapping("/test")
        public String test() {
            ServiceInstance instance = loadBalancerClient.choose("alibaba-nacos-discovery-server");
            String uri = instance.getUri() + "/hello?name=" + "fan";
            RestTemplate restTemplate = new RestTemplate();
            String forObject = restTemplate.getForObject(uri, String.class);
            return "invoke:" + uri + "return:" + forObject;
        }
    }
}
