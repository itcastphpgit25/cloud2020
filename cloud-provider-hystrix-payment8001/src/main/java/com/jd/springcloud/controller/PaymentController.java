package com.jd.springcloud.controller;

import com.jd.springcloud.service.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @GetMapping("/get/hystrix/ok/{id}")
    public String paymentInfo_ok(@PathVariable("id") Integer id){

        return  paymentService.paymentInfo(id);
    }


    @GetMapping("/get/hystrix/timeout/{id}")
    public String paymentInfo_timeout(@PathVariable("id") Integer id){

        return  paymentService.paymentInfo_timeout(id);
    }


    //=========断路器
    @GetMapping("/get/hystrix/circuit/{id}")
    public String getcircuitbreaker(@PathVariable("id") Integer id){
        final String breaker = paymentService.paymentCircuitBreaker(id);
        return breaker;
    }
}
