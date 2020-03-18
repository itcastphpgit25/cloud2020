package com.jd.springcloud.controller;

import com.jd.springcloud.service.IMessageProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class sendController {

    @Resource
    private IMessageProvider iMessageProvider;

    @GetMapping("/send/message")
    public String sendt1(){
        return iMessageProvider.send();
    }
}
