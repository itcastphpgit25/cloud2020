package com.atguigu.springcloud.service.Impl;

import com.atguigu.springcloud.dao.PaymentDao;
import com.atguigu.springcloud.service.PaymentService;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDao paymentDao;
    @Override
    public int insert(Payment payment) {
        int pt = paymentDao.insert(payment);
        return pt;
    }

    @Override
    public Payment selectById(Long id) {
        Payment i = paymentDao.selectById(id);
        return i;
    }
}
