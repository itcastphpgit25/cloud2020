package com.atguigu.rabbit.rabbitdemo;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitDemoApplicationTests {

	@Autowired
	RabbitTemplate rabbitTemplate;

    @Test
	public void sendMsg(){
		Map<String, Object> map = new HashMap<>();
		map.put("hehe","匹配的交换机队列你们好...");
		rabbitTemplate.convertAndSend("exchange.topic","atguigu.abc",map);
		System.out.println("消息发送成功");
	}
	@Test
	void contextLoads() {
	}

}
