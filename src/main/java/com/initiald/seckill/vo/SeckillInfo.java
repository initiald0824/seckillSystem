package com.initiald.seckill.vo;

import com.initiald.seckill.domain.OrderInfo;

/**
 * @author initiald0824
 * @date 2019/6/6 23:20
 */
public class SeckillInfo {

    private GoodsVo goodsVo;
    private OrderInfo orderInfo;

    public GoodsVo getGoodsVo() {
        return goodsVo;
    }

    public void setGoodsVo(GoodsVo goodsVo) {
        this.goodsVo = goodsVo;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }
}
