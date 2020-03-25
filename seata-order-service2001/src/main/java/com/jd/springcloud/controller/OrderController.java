package com.jd.springcloud.controller;

import com.jd.springcloud.domain.CommonResult;
import com.jd.springcloud.domain.Order;
import com.jd.springcloud.service.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/order/create")
    public CommonResult Creat(Order order){
        orderService.create(order);
        return new CommonResult(200,"订单创建成功");
    }


}
