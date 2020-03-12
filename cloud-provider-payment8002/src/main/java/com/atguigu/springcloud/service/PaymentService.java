package com.atguigu.springcloud.service;


import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {
    public int insert(Payment payment);
    public Payment selectById(@Param("id")Long id);
}
