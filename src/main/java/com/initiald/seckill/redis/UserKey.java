package com.initiald.seckill.redis;

/**
 * @author initiald0824
 * @date 2019/4/16 15:13
 */
public class UserKey extends BasePrefix {
    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");

    private UserKey(String prefix) {
        super(prefix);
    }
}
