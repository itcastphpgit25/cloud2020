package com.jd.springcloud.service;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements FeignService {
    @Override
    public CommonResult<Payment> paymentMethod(Long id) {
        return new CommonResult<>(444444,"服务降级返回");

    }
}
