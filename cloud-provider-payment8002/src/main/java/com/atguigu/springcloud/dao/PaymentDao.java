package com.atguigu.springcloud.dao;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

//与mybatis打交道的
@Mapper
public interface PaymentDao {

    public int insert(Payment payment);
    public Payment selectById(@Param("id")Long id);
}
