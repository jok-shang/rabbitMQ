package com.example.rabbitmqspringbootconsumer.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author 尚智江
 * @CreateDate 2023/6/5 0:28
 */
@Component
public class RabbitMQListener {

    // 监听队列
    @RabbitListener(queues = "boot_queue")
    public void ListenerQueue(Message message){
        System.out.println(new String(message.getBody()));
    }
}
