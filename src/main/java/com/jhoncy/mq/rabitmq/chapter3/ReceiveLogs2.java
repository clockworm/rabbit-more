package com.jhoncy.mq.rabitmq.chapter3;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by zhuangqi on 17-6-4.
 */
public class ReceiveLogs2 {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //通道绑定到名称为“logs”，类型为“fanout”的交换器
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        //创建一个非持久化、独立、自动删除的队列名称。
        String quequeName = channel.queueDeclare().getQueue();
        // 将我们的队列跟交换器进行绑定
        channel.queueBind(quequeName, EXCHANGE_NAME, "");
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(quequeName, true, consumer);
    }
}
