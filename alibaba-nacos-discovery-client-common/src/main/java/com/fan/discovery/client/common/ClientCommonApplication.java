package com.fan.discovery.client.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
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

        @GetMapping("/test1")
        public String test() {
            ServiceInstance instance = loadBalancerClient.choose("alibaba-nacos-discovery-server");
            String uri = instance.getUri() + "/hello?name=" + "test1";
            RestTemplate restTemplate = new RestTemplate();
            String forObject = restTemplate.getForObject(uri, String.class);
            return "invoke:" + uri + "return:" + forObject;
        }
    }

    @RestController
    @Slf4j
    static class TestController2 {

        @Autowired
        private RestTemplate restTemplate;

        @GetMapping("/test2")
        public String test() {
            //直接调用容器中的RestTemplate，采用nacos对应的服务名称来调用对应接口
            return restTemplate.getForObject("http://alibaba-nacos-discovery-server" + "/hello?name=test2", String.class);
        }
    }

    /**
     * 可以看到，在定义RestTemplate的时候，增加了@LoadBalanced注解，而在真正调用服务接口的时候，
     * 原来host部分是通过手工拼接ip和端口的，直接采用服务名的时候来写请求路径即可。在真正调用的时候，
     * Spring Cloud会将请求拦截下来，然后通过负载均衡器选出节点，并替换服务名部分为具体的ip和端口，从而实现基于服务名的负载均衡调用。
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
