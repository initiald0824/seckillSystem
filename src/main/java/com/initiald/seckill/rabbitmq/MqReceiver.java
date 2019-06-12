package com.initiald.seckill.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author initiald0824
 * @date 2019/6/12 15:08
 */

@Service
public class MqReceiver {

    private static Logger log = LoggerFactory.getLogger(MqReceiver.class);

    @RabbitListener(queues = MqConfig.QUEUE)
    public void receive(String message) {
        log.info("receive message " + message);
    }

    @RabbitListener(queues = MqConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        log.info("receive topic queue1 message: " + message);
    }

    @RabbitListener(queues = MqConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message) {
        log.info("receive topic queue2 message: " + message);
    }

    @RabbitListener(queues = MqConfig.HEADERS_QUEUE)
    public void receiveHeader(byte[] message) {
        log.info("receive header queue message: " + new String(message));
    }
}
