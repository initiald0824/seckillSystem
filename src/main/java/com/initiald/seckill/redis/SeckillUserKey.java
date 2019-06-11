package com.initiald.seckill.redis;

/**
 * @author initiald0824
 * @date 2019/5/24 15:53
 */
public class SeckillUserKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;

    public SeckillUserKey(String prefix, int expireSeconds) {
        super(prefix, expireSeconds);
    }

    public static SeckillUserKey token = new SeckillUserKey("tk", TOKEN_EXPIRE);

    public static SeckillUserKey getById = new SeckillUserKey("id", 0);
}
