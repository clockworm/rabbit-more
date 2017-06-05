package com.jhoncy.mq.rabitmq.chapter1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息生产者
 * Created by zhuangqi on 17-6-4.
 */

public class P {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建链接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ地址（安装地址）
        factory.setHost("localhost");
        //创建一个新的连接
        Connection connection = factory.newConnection();
        //创建一个频道
        Channel channel = connection.createChannel();
        //声明一个队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //发送消息的主体
        String message = "Hello World!";
        //发送消息到队列中
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println("P [x] Sent :"+message);
        //关闭频道和连接
        channel.close();
        connection.close();
    }
}
