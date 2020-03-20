package com.js.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Value("${server.port}")
    private String port;

    @GetMapping(value = "/payment/nacos/{id}")
    public String getpayment(@PathVariable("id") String id){
       return "nacos registry,serverPort:"+port+"\tid:"+id;
    }
}
