package com.initiald.seckill.redis;

/**
 * @author initiald0824
 * @date 2019/6/11 9:58
 */
public class GoodsKey extends BasePrefix {

    private GoodsKey(String prefix, int expireSeconds) {
        super(prefix, expireSeconds);
    }

    public static GoodsKey getGoodsList = new GoodsKey("gl", 60);
    public static GoodsKey getGoodsDetail = new GoodsKey("gd", 60);
    public static GoodsKey getSeckillGoodsStock = new GoodsKey("gs", 0);
}
