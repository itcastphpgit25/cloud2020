package com.jidong.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ConsumerzkMain_App80 {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerzkMain_App80.class,args);
    }
}
