package com.initiald.seckill.rabbitmq;

import com.initiald.seckill.domain.OrderInfo;
import com.initiald.seckill.domain.SeckillOrder;
import com.initiald.seckill.domain.SeckillUser;
import com.initiald.seckill.exception.GlobalException;
import com.initiald.seckill.redis.RedisService;
import com.initiald.seckill.result.CodeMsg;
import com.initiald.seckill.service.GoodsService;
import com.initiald.seckill.service.OrderService;
import com.initiald.seckill.service.SeckillService;
import com.initiald.seckill.vo.GoodsVo;
import com.initiald.seckill.vo.SeckillInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author initiald0824
 * @date 2019/6/12 15:08
 */

@Service
public class MqReceiver {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SeckillService seckillService;


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

    @RabbitListener(queues = MqConfig.SECKILL_QUEUE)
    public void receiveSeckill(String message) {
        SeckillMessage msg = RedisService.stringToBean(message, SeckillMessage.class);
        SeckillUser user = msg.getUser();
        long goodsId = msg.getGoodsId();

        // 判断库存
        GoodsVo goods = goodsService.getGoodsDetail(goodsId);
        int stock = goods.getGoodsStock();
        if (stock <= 0) {
            throw new GlobalException(CodeMsg.SECKILL_OVER);
        }

        // 判断是否已经秒杀到了
        SeckillOrder order = orderService.getSeckillOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            // 已经存在，重复秒杀
            return;
        }

        // 减库存， 下订单，写入秒杀订单
        OrderInfo orderInfo = seckillService.seckill(user, goods);

        SeckillInfo seckillInfo = new SeckillInfo();
        seckillInfo.setGoodsVo(goods);
        seckillInfo.setOrderInfo(orderInfo);
    }
}
