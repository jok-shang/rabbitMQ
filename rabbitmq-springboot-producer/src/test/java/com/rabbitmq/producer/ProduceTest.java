package com.rabbitmq.producer;

import com.rabbitmq.producer.config.RabbitMQConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author 尚智江
 * @CreateDate 2023/6/4 23:44
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProduceTest {

    // 1. 注入RabbitTemplate
    @Resource
    public RabbitTemplate rabbitTemplate;

    @Test
    public void testSend(){
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,"boot.haha","boot mq hello");
    }
}
