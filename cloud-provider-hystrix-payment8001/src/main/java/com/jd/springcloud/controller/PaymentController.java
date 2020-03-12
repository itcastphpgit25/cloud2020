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


    @GetMapping("/get/payment/timeout/{id}")
    public String paymentInfo_timeout(@PathVariable("id") Integer id){

        return  paymentService.paymentInfo_timeout(id);
    }
}
