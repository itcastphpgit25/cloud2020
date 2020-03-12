package com.atguigu.rabbit.rabbitdemo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class MyRabbitConfig {


    /**
     *
     * 死信
     */

    @Bean
    public Exchange createDelayExchange() {
        return new DirectExchange("delayExchange", true, false);
    }

    @Bean
    public Queue createDelayQueue(){
        Map<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange","deadExchange");  //代表消息死了去这个私信交换机
        map.put("x-dead-letter-routing-key","dead");
        map.put("x-message-ttl",60000);
        return new Queue("delayQueue",true,false,false,null);
    }

    @Bean
    public Binding DelayBinding(){
        return new Binding("delayQueue", Binding.DestinationType.QUEUE,"delayExchange","order",null);
    }

    //死信交换机
    @Bean
    public Exchange deadExchange() {
        return new DirectExchange("deadExchange", true, false);
    }

    //死信队列
    @Bean
    public Queue deadQueue(){
        return new Queue("deadQueue",true,false,false);
    }

    //死信交换机
    @Bean
    public Binding deadBinding(){
        return new Binding("deadQueue", Binding.DestinationType.QUEUE,"deadExchange","dead",null);
    }
    //================================================================================
    /**
     *
     * 全系统使用json进行转换
     * @return
     */
    @Bean
    public MessageConverter getConverter(){
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 容器中创建： Queue  Exchange  binding
     */
   @Bean
    public Queue createqueue(){
        return new Queue("hehehe",true,false,false);
    }

    @Bean
    public Exchange createExchange(){
        return new DirectExchange("direct-exchange-777");
    }

    /**
     *
     * public Binding(String destination,
     *                DestinationType destinationType,
     *                String exchange,
     *                String routingKey,
                      Map<String, Object> arguments)
     * @return
     */
    @Bean
    public Binding createBinding(){
        return new Binding("hehehe", Binding.DestinationType.QUEUE,"direct-exchange-777","hehehe",null);
    }


}
