package com.jd.springcloud.service.impl;

import com.jd.springcloud.dao.StorageDao;
import com.jd.springcloud.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StorageServiceImpl implements StorageService {
    final Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class);
    @Resource
    private StorageDao storageDao;
    @Override
    public void update(Long productId, Integer count) {
        logger.info("减库存开始");
        storageDao.update(productId,count);
        logger.info("减库存结束end");
    }
}
