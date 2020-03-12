package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentService;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String port;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.insert(payment);
        log.info("****插入结果:"+result);

        if(result>0)
        {
            return new CommonResult(200,"插入数据库成功,port:"+port,payment.getId());
        }else {
            return new CommonResult(444,"插入数据失败,port:"+port,null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> select(@PathVariable("id") Long id){
        Payment result = paymentService.selectById(id);
        log.info("****插入结果:"+result);

        if(result!=null)
        {
            return new CommonResult(200,"查询数据库成功,port:"+port,result);
        }else {
            return new CommonResult(444,"没有对应数据:"+id+",port:"+port,result);
        }
    }

    @GetMapping("/payment/id")
    public String getPort(){
        return port;
    }
}