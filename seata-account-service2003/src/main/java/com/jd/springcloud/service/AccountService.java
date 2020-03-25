package com.jd.springcloud.service;

import java.math.BigDecimal;

public interface AccountService {
    public void deacmoney(Long userId,BigDecimal money);
}
