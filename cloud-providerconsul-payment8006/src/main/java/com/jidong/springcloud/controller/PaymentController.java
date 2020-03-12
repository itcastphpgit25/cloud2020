package com.jidong.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PaymentController {
    @Value("${server.port}")
    private String port;

    @GetMapping("/payment/consul")
    public String getConsul(){
        return  "springcloud whith consul:"+port+"\t"+ UUID.randomUUID().toString();

    }
}
