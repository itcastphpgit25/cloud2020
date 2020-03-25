package com.jd.springcloud.service;

import org.apache.ibatis.annotations.Param;

public interface StorageService {
    void update(Long productId,Integer count);
}
