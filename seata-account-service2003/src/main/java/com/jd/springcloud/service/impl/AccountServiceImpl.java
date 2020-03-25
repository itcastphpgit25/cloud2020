package com.jd.springcloud.service.impl;

import com.jd.springcloud.dao.AccountDao;
import com.jd.springcloud.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Service
public class AccountServiceImpl implements AccountService {
    final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    @Resource
    private AccountDao accountDao;
    @Override
    public void deacmoney(Long userId, BigDecimal money) {
        logger.info("账户扣款开始");
        accountDao.deacmoney(userId,money);
        /*try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        logger.info("账户扣款结束");
    }
}
