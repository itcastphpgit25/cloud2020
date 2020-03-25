package com.jd.springcloud.service;


import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.micrometer.core.instrument.Meter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;
@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public String paymentInfo(Integer id) {
        return "线程池"+Thread.currentThread().getName()+"  paymentInfo,id: "+id;
    }



    @Override
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",
                    commandProperties ={@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")} )
    public String paymentInfo_timeout(Integer id) {

        //Integer a=10/0;

        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池"+Thread.currentThread().getName()+"  paymentInfo_timeout,id: "+id;
    }

    //降级调用的方法

    public String paymentInfo_TimeOutHandler(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + "  8001服务降级：系统繁忙或者运行报错，请稍后再试,id:" + id;
    }

    //----------服务熔断------------------666

    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value ="true" ), //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value ="10" ), //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value ="15000" ),  //时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value ="60" ) //错误率达到多少后跳闸
    })
    public String paymentCircuitBreaker(Integer id){
        if(id<0){
            throw new RuntimeException("...........id 不能为负数");
        }
        final String uuid = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t 调用成功，流水号为"+uuid;
    }
    public String paymentCircuitBreaker_fallback(Integer id){
        return "id不能为负数，请稍后重试，id:"+id;
    }
}
