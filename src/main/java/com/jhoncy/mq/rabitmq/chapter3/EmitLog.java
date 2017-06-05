package com.jhoncy.mq.rabitmq.chapter3;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by zhuangqi on 17-6-4.
 */
public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //声明一个名称为“logs”类型为"fanout" 的交换器
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        //分发消息
        for (int i = 0; i < 5; i++) {
            String message = "hello World ！" + i;
            //指定交换机分发消息
            channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
        }
        channel.close();
        connection.close();
    }
}
