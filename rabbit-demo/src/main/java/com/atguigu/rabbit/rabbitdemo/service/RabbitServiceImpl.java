package com.atguigu.rabbit.rabbitdemo.service;

import com.atguigu.rabbit.rabbitdemo.bean.Order;
import com.atguigu.rabbit.rabbitdemo.bean.User;
import com.fasterxml.jackson.databind.ser.impl.FailingSerializer;
import com.rabbitmq.client.Channel;
import com.sun.xml.internal.ws.api.model.MEP;
import com.sun.xml.internal.ws.util.Pool;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class RabbitServiceImpl implements RabbitService{
    @Autowired
    RabbitTemplate rabbitTemplate;


    /**
     * 用代码创建出Exchange,Queue,Binding
     *
     */
    @Autowired
    AmqpAdmin amqpAdmin;

    @Override
    public void createQueue() {
        /**
         *
         * name:队列名称
         * durable==true：队列持久化
         * exclusive==false：非排他模式，不止一个连接可以连到
         * autoDelete==false:不自动删除
         */
         String queue = amqpAdmin.declareQueue(new Queue("hahaha", true,false,false));
        System.out.println("创建队列成功");
    }

    @Override
    public void createExdchange() {
        amqpAdmin.declareExchange(new DirectExchange("direct-exchange-666"));
        System.out.println("交换机创建成功");
    }

    @Override
    public void createBind() {
        /**
         * public Binding(String destination, //目的地：指队列
         *                DestinationType destinationType, //跟谁绑定：交换机跟交换机，还是交换机和队列
         *                String exchange,
         *                String routingKey,
                          Map<String, Object> arguments)
         */
        amqpAdmin.declareBinding(new Binding("hahaha", Binding.DestinationType.QUEUE,"direct-exchange-666","ccc",null));
        System.out.println("交换机和队列绑定成功");
    }


    @Override
    public void sendMsg(){
        final User user = new User(1, "lch", "123",23);
        //使用json转换器，防止在客户端队列中的数据出现乱码
        //rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.convertAndSend("exchange.topic","atguigu.abc",user);
        System.out.println("消息发送成功");
    }


//======================================================================================

    /**
     *
     * 定时关单发送信息
     */

    @Override
    public void stopdead() throws InterruptedException {
        Order order1 = new Order(UUID.randomUUID().toString(), "PAYED");
        System.out.println("订单order1已经发出");
        rabbitTemplate.convertAndSend("delayExchange","order",order1);



        Order order2 = new Order(UUID.randomUUID().toString(), "stop");
        Thread.sleep(20000);
        System.out.println("订单order2已经发出");
        rabbitTemplate.convertAndSend("delayExchange","order",order2);


        Order order3 = new Order(UUID.randomUUID().toString(), "PAYED");
        Thread.sleep(20000);
        System.out.println("订单order3已经发出");
        rabbitTemplate.convertAndSend("delayExchange","order",order3);
    }


    //监听死信休息队列中的过期信息
    @RabbitListener(queues = "deadQueue")
    public void consumerb(Order order, Channel channel, Message message) throws IOException {
        System.out.println("收到超时订单..."+order);
        if (order.getStatus().equals("PAYED")){
            System.out.println("此订单已经支付，，，不用操作");
        }else {
            System.out.println("此订单已经关闭");
        }

        //手动将超时的订单关掉
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

//===================================================
    /**
     *
     * 手动ack
     * 开启手动确认回复模式：
     *  1.消息丢失
     *    1.手动回复ack(确认一个消息收到了并且任务完成了就手动ack)
     */
    //@RabbitListener(queues = "atguigu.emps")
    public void consumerA(Channel channel,Message message) throws Exception{
        System.out.println("【1号获取队列返回的消息】："+message.getMessageProperties().getDeliveryTag());
        //如果某个消息没回复，这个消息是unacked状态，而且不会继续发过来
        if(message.getMessageProperties().getDeliveryTag()%2==0) {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }


    /**
     *
     * 方法上可以有三种参数
     *  1、Message收到的消息封装到message
     *  2、使用发送时的数据类型来接收
     *
     *  注意：默认的是 接收消息后就消息从队列删除
     */
    //@RabbitListener(queues = "atguigu")
    public void consumer(User user, Channel channel,Message message) throws Exception{
        //System.out.println("【1号获取队列返回的消息】："+user);

        /**
         * void basicReject(long deliveryTag, boolean requeue):手动拒绝消息的方法
         * deliveryTag：接收消息的标签
         * requeue：true是拒绝后重新放回队列，false：拒绝后丢弃
         * 效果：会一直接收到消息，因为一下的设置：拒绝后又放回队列中了，然后监视队列的这个消费者又得到了
         *
         */
        channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);

        //告诉RabbitMQ我收到消息,发送确认收到:false:只确定当前消息收到了，true:确定所有消息都收到了
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        /*try {
            //假设订单处理失败，然后在catch中间将订单重新放回队列中
            if(user.getAge()%2==0){
                System.out.println("【1号服务器......收到了消息并处理完成】："+user);
            }else {
                throw new RuntimeException();
            }
        }catch (Exception e) {
            System.out.println("【1号服务器出现故障，消息拒绝】");
            //拒绝了消息，并重新入队
            *//**
             * 不发送确认收到，
             * 第二个参数：false:拒绝管道channel上当前接收到的这一个消息，而不是拒绝多个; true:拒绝所有消息
             * 第三个参数true：重新入队；  false:丢弃消息，从队列中删除
             *
             * ack和nack区别：ack确认收到  nack:不给回复确认收到
             * nack和reject：很相似，reject拒绝后下次不会在发给这个消费者了，nack是接收到消息后没响应，不过下次这个消费者还会得到这个消息
             *//*

            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
        }*/
    }

    //@RabbitListener(queues ="atguigu")
    public void cosumer(User user,Channel channel,Message message)throws Exception{
        System.out.println("【2号服务器获取队列返回的消息】："+user);

        /**
         * void basicReject(long deliveryTag, boolean requeue):手动拒绝消息的方法
         * deliveryTag：接收消息的标签
         * requeue：true是拒绝后重新放回队列，false：拒绝后丢弃
         * 效果：会一直接收到消息，因为一下的设置：拒绝后又放回队列中了，然后监视队列的这个消费者又得到了
         *
         */
        //channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);


        //告诉RabbitMQ我收到消息,发送确认收到:false:只确定当前消息收到了，true:确定所有消息都收到了
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        try {
            //假设订单处理失败，然后在catch中间将订单重新放回队列中
            if(user.getAge()%2!=0){
                System.out.println("【2号服务器......收到了消息并处理完成】："+user);
            }else {
                throw new RuntimeException();
            }
        }catch (Exception e) {
            System.out.println("【2号服务器出现故障，消息拒绝】");
            //拒绝了消息，并重新入队
            /**
             * 不发送确认收到，
             * 第二个参数：false:拒绝管道channel上当前接收到的这一个消息，而不是拒绝多个，
             * 第三个参数true：重新入队
             *
             * ack和nack区别：ack确认收到  nack:不给回复确认收到
             * nack和reject：很相似，reject拒绝后下次不会在发给这个消费者了，nack是接收到消息后没响应，不会下次这个消费者还会得到这个消息
             */

            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
        }
    }


}
