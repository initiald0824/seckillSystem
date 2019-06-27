package com.initiald.seckill.service;

import com.initiald.seckill.domain.OrderInfo;
import com.initiald.seckill.domain.SeckillOrder;
import com.initiald.seckill.domain.SeckillUser;
import com.initiald.seckill.exception.GlobalException;
import com.initiald.seckill.redis.RedisService;
import com.initiald.seckill.redis.SeckillKey;
import com.initiald.seckill.result.CodeMsg;
import com.initiald.seckill.vo.GoodsVo;
import com.initiald.seckill.vo.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author initiald0824
 * @date 2019/6/6 23:06
 */
@Service
public class SeckillService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisService redisService;

    @Transactional(rollbackFor = Exception.class)
    public OrderInfo seckill(SeckillUser user, GoodsVo goods) {
        // 减库存 下订单 写入秒杀订单
        boolean success = goodsService.reduceStock(goods);
        if (success) {
            // order_info seckill_order
            return orderService.createOrder(user, goods);
        } else {
            setGoodsOver(goods.getId());
            return null;
        }
    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(SeckillKey.isGoodsOver, ""+goodsId, true);
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(SeckillKey.isGoodsOver, ""+goodsId);
    }

    public OrderDetail getOrderDetail(SeckillUser user, long orderId) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setSeckillUser(user);
        OrderInfo orderInfo = orderService.getOrderInfoById(orderId);
        if (orderInfo == null) {
            throw new GlobalException(CodeMsg.ORDER_NOT_EXIST);
        }
        orderDetail.setOrderInfo(orderInfo);
        orderDetail.setSeckillGoodsVo(goodsService.getGoodsDetail(orderInfo.getGoodsId()));
        return orderDetail;
    }

    public long getSeckillResult(Long userId, long goodsId) {
        SeckillOrder order = orderService.getSeckillOrderByUserIdGoodsId(userId, goodsId);
        if (order != null) {
            return order.getOrderId();
        } else {
            boolean isOver = getGoodsOver(goodsId);
            if (isOver) {
                return -1;
            } else {
                return 0;
            }
        }
    }


}
