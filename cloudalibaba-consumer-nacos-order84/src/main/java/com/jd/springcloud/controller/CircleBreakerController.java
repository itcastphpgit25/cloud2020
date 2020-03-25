package com.jd.springcloud.controller;

import ch.qos.logback.core.joran.conditional.ThenOrElseActionBase;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.jd.springcloud.service.FeignService;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class CircleBreakerController {

    private static final String URL="http://nacos-payment-provider";
    @Resource
    private RestTemplate restTemplate;


    @Resource
    private FeignService feignService;

    @GetMapping("/consumer/fallback/{id}")
    //@SentinelResource(value = "fallback") //什么都没配置
    //@SentinelResource(value = "fallback",fallback = "handlerFallback") //fallback只负责业务异常
    //@SentinelResource(value = "fallback",blockHandler = "blockHandler") //blockHandler只负责sentinel控制台配置违规
    @SentinelResource(value = "fallback",
            fallback = "handlerFallback",
            blockHandler = "blockHandler",
    exceptionsToIgnore = {IllegalArgumentException.class})
    public CommonResult<Payment> paymentMethod(@PathVariable("id") Long id){

        CommonResult<Payment> result = restTemplate.getForObject(URL + "/paymentSQL/" + id, CommonResult.class,id);
        if(id==4){
            throw new IllegalArgumentException("IllegalArgumentException,非法参数异常");
        }else if(result.getData()==null){
            throw new NullPointerException("NullPointerException,没有查到对应的记录");
        }
        return result;
    }
    //只配置fallback
    public CommonResult<Payment> handlerFallback(@PathVariable("id") Long id,Throwable e){
        return new CommonResult<>(444,"兜底异常handlerFallback："+e.getMessage(),new Payment(id,null));
    }

    //只配置blockHandler
    public CommonResult<Payment> blockHandler(@PathVariable("id") Long id,BlockException e){
        return new CommonResult<>(445,"sentinel配置blockHandler："+e.getMessage(),new Payment(id,null));
    }


    /**
     * openFeign：远程调用
     */
    @GetMapping("/get/feign/{id}")
    public CommonResult<Payment> getFeign(@PathVariable("id") Long id){
        return feignService.paymentMethod(id);
    }

}
