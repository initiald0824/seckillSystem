package com.initiald.seckill.redis;

/**
 * @author initiald0824
 * @date 2019/4/16 15:17
 */
public class OrderKey extends BasePrefix {
    public OrderKey(String prefix, int expireSeconds) {
        super(prefix, expireSeconds);
    }
}
