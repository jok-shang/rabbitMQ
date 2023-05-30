package com.rabbitmq.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author 尚智江
 * @CreateDate 2023/5/29 23:05
 */
public class ProducerWorkQueue {

    /**
     * 工作流生产者（很多个消费者监听同一个队列只能有一个消费者收到）
     * 和 rabbitmq-consumer 模块下的ConsumerWorkQueue1
     * 和ConsumerWorkQueue2 先启动消费者
     * 消费者轮流消费生产者生产的消息
     * @param args
     * @throws IOException
     * @throws TimeoutException
     */
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
        // 6. 发送消息
        /*
        basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
        参数：
            1. exchange：交换机名称，简单模式下交换机会使用默认的
            2. routingKey： 路由名称
            3. props: 配置信息
            4. body: 发送消息数据
         */
        for (int i = 0;i<10;i++){
            String body = "hello rabbitmq" + i;
            channel.basicPublish("","workQueues",null,body.getBytes());
        }
        // 7. 释放资源
        channel.close();
        connection.close();
    }

}
