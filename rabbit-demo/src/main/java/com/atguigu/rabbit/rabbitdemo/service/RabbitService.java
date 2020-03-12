package com.atguigu.rabbit.rabbitdemo.service;

public interface RabbitService {
    public  void sendMsg();

    public  void createQueue();

    public  void createExdchange();

    public  void createBind();

    public void stopdead() throws InterruptedException;
}
