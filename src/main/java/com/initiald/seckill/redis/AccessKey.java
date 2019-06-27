package com.initiald.seckill.redis;

/**
 * @author initiald0824
 * @date 2019/6/20 14:41
 */
public class AccessKey extends BasePrefix {
    private AccessKey(String prefix, int expireSeconds) {
        super(prefix, expireSeconds);
    }

    public static AccessKey access = new AccessKey("ak", 5);

    public static AccessKey withExpire(int expireSeconds) {
        return new AccessKey("access", expireSeconds);
    }
}
