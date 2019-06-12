package com.initiald.seckill.rabbitmq;

import com.initiald.seckill.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author initiald0824
 * @date 2019/6/12 15:07
 */

@Service
public class MqSender {

    private Logger log = LoggerFactory.getLogger(MqSender.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(Object message) {
        String msg = RedisService.beanToString(message);
        log.info("send message " + msg);
        amqpTemplate.convertAndSend(MqConfig.QUEUE, msg);
    }

    public void sendTopic(Object message) {
        String msg = RedisService.beanToString(message);
        log.info("send to topic message " + msg);
        amqpTemplate.convertAndSend(MqConfig.TOPIC_EXCHANGE, "topic.key1", msg+"1");
        amqpTemplate.convertAndSend(MqConfig.TOPIC_EXCHANGE, "topic.key2", msg+"2");
    }

    public void sendFanout(Object message) {
        String msg = RedisService.beanToString(message);
        log.info("send to fanout message " + msg);
        amqpTemplate.convertAndSend(MqConfig.FANOUT_EXCHANGE, "", msg);
    }

    public void sendHeader(Object message) {
        String msg = RedisService.beanToString(message);
        log.info("send to header message " + msg);
        MessageProperties properties = new MessageProperties();
        properties.setHeader("header1", "value1");
        properties.setHeader("header2", "value2");
        Message obj = new Message(msg.getBytes(), properties);
        amqpTemplate.convertAndSend(MqConfig.HEADERS_EXCHANGE, "", obj);
    }
}
