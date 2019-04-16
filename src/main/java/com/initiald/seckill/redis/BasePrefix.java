package com.initiald.seckill.redis;

/**
 * @author initiald0824
 * @date 2019/4/16 15:00
 */
public abstract class BasePrefix implements KeyPrefix {

    private String prefix;
    private int expireSeconds;

    public BasePrefix() {
    }

    public BasePrefix(String prefix) {
        this(prefix, 0);
    }

    public BasePrefix(String prefix, int expireSeconds) {
        this.prefix = prefix;
        this.expireSeconds = expireSeconds;
    }

    /**
     * 默认0代表永不过期
     * @return
     */
    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
