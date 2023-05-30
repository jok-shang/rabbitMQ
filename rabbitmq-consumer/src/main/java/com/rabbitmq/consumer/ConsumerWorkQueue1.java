package com.rabbitmq.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author 尚智江
 * @CreateDate 2023/5/29 23:29
 */
public class ConsumerWorkQueue1 {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1. 创建链接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 2. 设置参数  http://192.168.5.130/
        factory.setHost("192.168.5.130"); // ip
        factory.setPort(5672); // 设置端口
        factory.setVirtualHost("/"); // 虚拟机 默认 /
        factory.setUsername("guest");
        factory.setPassword("guest");
        // 3. 创建连接 connection
        Connection connection = factory.newConnection();
        // 4. 创建channel
        Channel channel = connection.createChannel();
        // 5. 创建队列
//        queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
        /*
        参数：
        1. queue：队列名称
        2. durable： 是否持久化,当mq重启后还在
        3. exclusive：
                是否独占。只能有一个消费者监听这个队列
                当Connection关闭时，是否删除队列
         4. autoDelete：是否自动删除，当没有Consumer时，自动删除
         5. arguments： 参数
         */
        channel.queueDeclare("workQueues",true,false,false,null);
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
                //                super.handleDelivery(consumerTag, envelope, properties, body);
            }
        };
        channel.basicConsume("workQueues",true,consumer);
        // 7. 不需要释放资源
    }
}
