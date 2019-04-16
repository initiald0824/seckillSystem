package com.initiald.seckill.redis;

/**
 * @author initiald0824
 * @date 2019/4/16 14:57
 */
public interface KeyPrefix {
    public int expireSeconds();

    public String getPrefix();
}
