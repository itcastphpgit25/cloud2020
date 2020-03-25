package com.jd.springcloud.service;

import com.jd.springcloud.domain.Order;
import org.apache.ibatis.annotations.Param;

public interface OrderService {
    void create(Order order);

}
