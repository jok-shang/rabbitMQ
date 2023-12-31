package com.rabbitmq.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author 尚智江
 * @CreateDate 2023/6/2 21:54
 */
public class ProducerRouting {
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

        // 5. 创建交换机
        /*
        exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments)
        参数：
            1. exchange：交换机名称
            2. type: 交换机类型
                DIRECT("direct"),:定向
                FANOUT("fanout"),:扇形(广播)，发送消息到每一个与之绑定的队列。
                TOPIC("topic"),通配符的方式
                HEADERS("headers");参数匹配
            3. durable:是否持久化
            4. autoDelete:自动删除
            5. internal: 内部使用。一般false
            6. arguments: 参数
         */
        String exchangeName = "test_direct";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true, false, false, null);
        // 6. 创建队列
        String queue1Name = "test_direct_queue1";
        String queue2Name = "test_direct_queue2";
        channel.queueDeclare(queue1Name, true, false, false, null);
        channel.queueDeclare(queue2Name, true, false, false, null);
        // 7. 绑定队列和交换机
        /*
        queueBind(String queue, String exchange, String routingKey)
        参数：
            1. queue: 队列名称
            2. exchange: 交换机名称
            3. routingKey: 路由键，绑定规则
                 如果交换机的类型为：fanout，routingKey设置为""
         */
        // 队列1的绑定 error
        channel.queueBind(queue1Name, exchangeName, "error");
        // 队列2绑定 info error warning
        channel.queueBind(queue2Name, exchangeName, "info");
        channel.queueBind(queue2Name, exchangeName, "error");
        channel.queueBind(queue2Name, exchangeName, "warning");
        // 8. 发送消息
                /*
        basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
        参数：
            1. exchange：交换机名称，简单模式下交换机会使用默认的
            2. routingKey： 路由名称
            3. props: 配置信息
            4. body: 发送消息数据
         */
        String body = "日志信息：张三调用方法，日志级别： info--";
        channel.basicPublish(exchangeName, "error", null, body.getBytes());
        // 9. 释放资源
        channel.close();
        connection.close();
    }
}
