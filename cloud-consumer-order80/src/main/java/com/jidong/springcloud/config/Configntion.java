package com.jidong.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Configntion {

    /**
     *
     * 远程调用使用：RestTemplate
     * @return
     */
    @Bean
    //@LoadBalanced  //使用此注解赋予RestTemplate负载均衡能力
    public RestTemplate getResTemplate(){
        return new RestTemplate();
    }
}
