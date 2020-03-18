package com.jidong.com.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.jidong.com.service.PaymentFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderFeignController {

    @Autowired
    private PaymentFeignService paymentFeignService;

    @GetMapping("/create/payment")
    public CommonResult<Payment> getPayment(@PathVariable("id")Long id){

        final CommonResult<Payment> result = paymentFeignService.select(id);
        return result;
    }

    @GetMapping("/get/consumer/timeout")
    public String getTimeoutPort(){

        //openfeign-ribbon,客户端一般默认是等待1秒钟的
        return paymentFeignService.getTimeoutPort();
    }
}
