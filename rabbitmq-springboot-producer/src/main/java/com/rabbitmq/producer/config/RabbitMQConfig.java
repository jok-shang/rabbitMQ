package com.rabbitmq.producer.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 尚智江
 * @CreateDate 2023/6/4 23:17
 */
@Configuration
public class RabbitMQConfig {
    // 1. 交换机
    // DirectExchange 需要将一个队列绑定到交换机上，要求该消息与一个特定的路由键完全匹配
    // FanoutExchange 消息广播
    // TopicExchange 通配符
    // HeadersExchange不处理路由键

    public static final String EXCHANGE_NAME = "boot_topic_exchange";
    public static final String QUEUE_NAME = "boot_queue";
    @Bean("bootExchange")
    public Exchange bootExchange(){
        /*
        durable 持久化
        antoDelete() 是否自动删除
         */
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }
    // 2.Queue队列
    @Bean("bootQueue")
    public Queue bootQueue(){
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    // 3.队列和交换机绑定关系 binding
    /*
    知道哪个队列
    知道哪个交换机
    设置 routing key
     */
    @Bean
    public Binding bindQueueExchange(@Qualifier("bootQueue")Queue queue,@Qualifier("bootExchange")Exchange exchange){
        // 绑定队列名，交换机和路由key
        return BindingBuilder.bind(queue).to(exchange).with("boot.#").noargs();
    }
}
