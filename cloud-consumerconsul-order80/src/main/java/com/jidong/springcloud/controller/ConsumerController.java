package com.jidong.springcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class ConsumerController {
    private static final String url="http://consul-provider-payment";
    @Resource
    private RestTemplate restTemplate;


    @GetMapping("/consumer/payment/consul")
    public String getConsul(){
        String result = restTemplate.getForObject(url + "/payment/consul", String.class);

        return result;
    }
}
