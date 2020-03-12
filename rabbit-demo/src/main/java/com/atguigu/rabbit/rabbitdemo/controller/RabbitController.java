package com.atguigu.rabbit.rabbitdemo.controller;

import com.atguigu.rabbit.rabbitdemo.service.RabbitService;
import com.atguigu.rabbit.rabbitdemo.service.RabbitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitController {

    @Autowired
    RabbitService rabbitService;

    @GetMapping("/send")
    public String sendMessing(){
        rabbitService.sendMsg();
        return "成功";
    }

    @GetMapping("/create")
    public String create(){
        rabbitService.createExdchange();
        rabbitService.createQueue();
        rabbitService.createBind();
        return "ok";
    }

    @GetMapping("/dead")
    public String dead() throws InterruptedException {
        rabbitService.stopdead();
        return "ok";
    }
}
