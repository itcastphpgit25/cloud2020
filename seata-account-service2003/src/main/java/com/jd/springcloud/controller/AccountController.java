package com.jd.springcloud.controller;

import com.jd.springcloud.domain.CommonResult;
import com.jd.springcloud.service.AccountService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.applet.Main;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController
public class AccountController {

    @Resource
    private AccountService accountService;

    @PostMapping("/account/decrease")
    public CommonResult deaccount(@RequestParam("userId") Long userId, @RequestParam("money")BigDecimal money){
        accountService.deacmoney(userId, money);
        return new CommonResult(200,"余额扣除成功");
    }
}
