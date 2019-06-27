package com.initiald.seckill.rabbitmq;

import com.initiald.seckill.domain.SeckillUser;

/**
 * @author initiald0824
 * @date 2019/6/13 14:43
 */
public class SeckillMessage {

    private SeckillUser user;
    private long goodsId;

    public SeckillUser getUser() {
        return user;
    }

    public void setUser(SeckillUser user) {
        this.user = user;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
