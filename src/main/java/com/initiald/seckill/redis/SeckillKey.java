package com.initiald.seckill.redis;

/**
 * @author initiald0824
 * @date 2019/6/13 20:53
 */
public class SeckillKey extends BasePrefix {


    private SeckillKey(String prefix) {
        super(prefix);
    }

    private SeckillKey(String prefix, int expireSeconds) {
        super(prefix, expireSeconds);
    }

    public static SeckillKey isGoodsOver = new SeckillKey("go");
    public static SeckillKey getSeckillPath = new SeckillKey("sp", 60);
}
