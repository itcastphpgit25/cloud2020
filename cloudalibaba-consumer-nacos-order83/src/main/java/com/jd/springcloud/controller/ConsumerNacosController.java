package com.jd.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class ConsumerNacosController {

    @Resource
    private RestTemplate restTemplate;

    @Value("${server-url.nacos-user-service}")
    private String uri;

    @GetMapping("/get/payment/nacos/{id}")
    public String getPayment(@PathVariable("id")String id){
        String result = restTemplate.getForObject(uri + "/payment/nacos/" + id, String.class);
        return result;
    }
}
