package com.initiald.seckill.vo;

import com.initiald.seckill.domain.OrderInfo;
import com.initiald.seckill.domain.SeckillUser;

/**
 * @author initiald0824
 * @date 2019/6/10 11:27
 */
public class OrderDetail {

    private SeckillGoodsVo seckillGoodsVo;
    private OrderInfo orderInfo;
    private SeckillUser seckillUser;

    public SeckillGoodsVo getSeckillGoodsVo() {
        return seckillGoodsVo;
    }

    public void setSeckillGoodsVo(SeckillGoodsVo seckillGoodsVo) {
        this.seckillGoodsVo = seckillGoodsVo;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public SeckillUser getSeckillUser() {
        return seckillUser;
    }

    public void setSeckillUser(SeckillUser seckillUser) {
        this.seckillUser = seckillUser;
    }
}
