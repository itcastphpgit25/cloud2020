package com.jd.springcloud.service.fallbackImpl;

import com.jd.springcloud.service.PaymentFeignClientService;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentFeignClientService {
    @Override
    public String paymentInfo_ok(Integer id) {
        return "------PaymentFallbackService fall back-paymentInfo_OK hahaha";
    }

    @Override
    public String paymentInfo_timeout(Integer id) {
        return "------PaymentFallbackService fall back-paymentInfo_timeout wuwuwu";
    }
}
