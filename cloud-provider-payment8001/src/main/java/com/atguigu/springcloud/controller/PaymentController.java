package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentService;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Value("${server.port}")
    private String port;

    @Resource
    private DiscoveryClient discoveryClient;

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
            return new CommonResult(444,"没有对应数据:"+id+"port:"+port,result);
        }
    }

    @GetMapping("/payment/discovery")
    public Object SelectServer(){
        final List<String> services = discoveryClient.getServices();
        for (String element:services) {
            System.out.println("****全部服务信息:"+element);
        }

        //CLOUD-PAYMENT-SERVICE:获取这个微服务名下的所有服务的信息，这个名下的微服务有两个服务端
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");

        for (ServiceInstance instance:instances) {
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }
        return this.discoveryClient;
    }

    @GetMapping("/payment/id")
    public String getPort(){
        return port;
    }


    /**
     *
     * 模拟超时
     * @return
     */
    @GetMapping("/get/payment/timeout")
    public String getTimeoutPort() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        return port;
    }

}