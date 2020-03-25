package com.jd.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@RestController
public class ProviderController {
    @Value("${server.port}")
    private String port;

    private static Map<Long,Payment> map=new HashMap<Long,Payment>();
    static {
        map.put(1L,new Payment(1L,"666"));
        map.put(2L,new Payment(2L,"888"));
        map.put(3L,new Payment(3L,"999"));
    }


    @GetMapping("/paymentSQL/{id}")
    public CommonResult<Payment> paymentMethod(@PathVariable("id") Long id){

        Payment payment = map.get(id);
        return new CommonResult<Payment>(200,"success-->port:"+port,payment);
    }
}
