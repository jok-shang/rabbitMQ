package com.rabbitmq.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author 尚智江
 * @CreateDate 2023/6/4 22:51
 */
public class ConsumerTopic1 {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1. 创建链接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 2. 设置参数  http://192.168.5.130/
        factory.setHost("192.168.5.133"); // ip
        factory.setPort(5672); // 设置端口
        factory.setVirtualHost("/"); // 虚拟机 默认 /
        factory.setUsername("guest");
        factory.setPassword("guest");
        // 3. 创建连接 connection
        Connection connection = factory.newConnection();
        // 4. 创建channel
        Channel channel = connection.createChannel();

        String queue1Name = "test_topic1_queue1";
        String queue2Name = "test_topic2_queue2";
        // 6. 接受消息
        /*
        basicConsume(String queue, boolean autoAck, Consumer callback)
        参数：
            1. queue：队列名称
            2. autoAck: 是否自动确认
            3. callback：回调对象
         */
        Consumer consumer = new DefaultConsumer(channel){
            /*
             回调方法: 当收到消息后， 会自动执行该方法
             1. consumerTag: 标识
             2. envelope: 获取一些信息，交换机，路由key。。。
             3. properties: 配置信息
             4. body: 数据
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("msg:" + new String(body));
                System.out.println("topic将日志信息存储到数据库");
                //                super.handleDelivery(consumerTag, envelope, properties, body);
            }
        };
        channel.basicConsume(queue1Name,true,consumer);
        // 7. 不需要释放资源
    }
}
