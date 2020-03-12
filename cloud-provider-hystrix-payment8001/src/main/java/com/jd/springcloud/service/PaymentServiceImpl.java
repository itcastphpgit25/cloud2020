package com.jd.springcloud.service;


import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public String paymentInfo(Integer id) {
        return "线程池"+Thread.currentThread().getName()+"  paymentInfo,id: "+id;
    }

    @Override
    public String paymentInfo_timeout(Integer id) {

        int timeNumber=3;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池"+Thread.currentThread().getName()+"  paymentInfo_timeout,id: "+id+"  耗时"+timeNumber+"s";
    }
}
