package com.jd.springcloud.controller;

import com.jd.springcloud.service.PaymentFeignClientService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class ConsumerController {

    @Resource
    private PaymentFeignClientService paymentHystrixService;

    @GetMapping("/get/payment/ok/{id}")
    public String getPayment(@PathVariable("id") Integer id){
       String infoOk = paymentHystrixService.paymentInfo_ok(id);
        return infoOk;
    }

    @GetMapping("/get/paymenttimeout/{id}")
    /*@HystrixCommand(fallbackMethod = "paymentTimeoutFallbackMethod",
                    commandProperties ={
                         @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000")}
    )*/
    @HystrixCommand
    public String getPayment_timeout(@PathVariable("id") Integer id){
        //int i=10/0;
        String infoOk_timeout = paymentHystrixService.paymentInfo_timeout(id);
        return infoOk_timeout;
    }

    //降级调用的方法
    public String paymentTimeoutFallbackMethod(@PathVariable("id") Integer id){
        return "线程池："+Thread.currentThread().getName()+"  80服务降级：系统繁忙或者运行报错，请稍后再试,id:"+id;
    }


    public String payment_Global_FallbackMethod(){
        return "全局异常处理信息，服务降级，请稍后重试。";
    }
}
