package com.jidong.springcloud.Controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class consumerController {

    @Autowired
    private RestTemplate restTemplate;

    //单机版
    //private static final String url="http://localhost:8001";

    //负载均衡cloud-payment-service
    private static final String url="http://CLOUD-PAYMENT-SERVICE";

    @GetMapping("/consumer/get/{id}")
    public CommonResult<Payment> restorder(@PathVariable("id")String id){
        //CommonResult commonResult = restTemplate.getForObject(url + "/payment/get/" + id, CommonResult.class);
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(url + "/payment/get/" + id, CommonResult.class);
        if(entity.getStatusCode().is2xxSuccessful()){
            log.info("状态码："+entity.getStatusCode()+"\t"+"，头信息："+entity.getHeaders());
            return entity.getBody();
        }else {
            return new CommonResult<Payment>(444,"操作失败");
        }
    }


    @GetMapping("/consumer/create")
    public CommonResult<Payment> insert(Payment payment){
        CommonResult commonResult = restTemplate.postForObject(url + "/payment/create", payment, CommonResult.class);
        return commonResult;

    }
}
