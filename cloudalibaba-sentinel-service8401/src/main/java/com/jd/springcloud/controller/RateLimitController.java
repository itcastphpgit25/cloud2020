package com.jd.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.jd.springcloud.myhandler.CustomerBlockHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {
    @GetMapping("/byResource")
    @SentinelResource(value = "byResource",blockHandler = "handlerException")
   public CommonResult byResource(){
       return new CommonResult(200,"按资源名称进行限流OK",new Payment(2020l,"001"));
   }

   public CommonResult handlerException(BlockException exception){
       return new CommonResult(444,exception.getClass().getCanonicalName()+"服务不可用");
   }



    @GetMapping("/byResource/byurl")
    @SentinelResource(value = "byurl",blockHandler = "handlerException1")
    public CommonResult byUrl(){
        return new CommonResult(200,"按资源名称进行限流OK",new Payment(2020l,"001"));
    }

    public CommonResult handlerException1(BlockException exception){
        return new CommonResult(444,exception.getClass().getCanonicalName()+"服务不可用");
    }

    @GetMapping("/rateLimit/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler",
            blockHandlerClass = CustomerBlockHandler.class, //指定兜底方法统一处理类
            blockHandler = "handlerException2")  //指定兜底方法统一处理类下的指定方法
    public CommonResult customerBlockHandler(){
        return new CommonResult(200,"按客户自定义OK",new Payment(2020l,"003"));
    }
}
