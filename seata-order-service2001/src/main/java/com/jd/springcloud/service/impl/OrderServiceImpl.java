package com.jd.springcloud.service.impl;

import com.jd.springcloud.dao.OrderDao;
import com.jd.springcloud.domain.Order;
import com.jd.springcloud.service.AccountService;
import com.jd.springcloud.service.OrderService;
import com.jd.springcloud.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {


    @Resource
    private OrderDao orderDao;

    @Resource
    private StorageService storageService;

    @Resource
    private AccountService accountService;


    /**
     *下订单(创建订单)--》减库存---》减余额--》修改订单状态
     *
     * @param order
     */
    @Override
    @GlobalTransactional(name = "lch",rollbackFor = Exception.class)//遇到任何异常都会被回滚
    public void create(Order order) {
        log.info("----->开始创建订单");
        //1.创建订单
        orderDao.create(order);

        log.info("----->从订单为服务开始调用库存，做扣减count");
        //2.扣减库存
        storageService.decrease(order.getProductId(),order.getCount());
        log.info("----->从订单为服务开始调用库存，做扣减end");

        log.info("----->从余额微服务开始调用账户，做扣减Money");
        //3.扣减账户
        accountService.deaccount(order.getUserId(),order.getMoney());
        log.info("----->从余额微服务开始调用账户，做扣减end");

        //4.修改订单状态，从零到1,1代表已经完成
        log.info("----->修改订单状态开始");
        orderDao.update(order.getUserId(),0);
        log.info("----->修改订单状态end");

        log.info("下订单完成...");

    }


}
