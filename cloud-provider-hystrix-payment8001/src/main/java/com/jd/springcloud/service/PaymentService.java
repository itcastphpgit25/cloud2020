package com.jd.springcloud.service;

import org.springframework.web.bind.annotation.PathVariable;

public interface PaymentService {
    public String paymentInfo(Integer id);

    public String paymentInfo_timeout(Integer id);


    public String paymentCircuitBreaker(Integer id);
}
