package com.initiald.seckill.service;

import com.initiald.seckill.domain.OrderInfo;
import com.initiald.seckill.domain.SeckillUser;
import com.initiald.seckill.vo.GoodsVo;
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

    @Transactional(rollbackFor = Exception.class)
    public OrderInfo seckill(SeckillUser user, GoodsVo goods) {
        // 减库存 下订单 写入秒杀订单
        goodsService.reduceStock(goods);
        // order_info seckill_order
        return orderService.createOrder(user, goods);
    }
}
