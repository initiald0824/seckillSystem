package com.initiald.seckill.config;

import com.initiald.seckill.domain.SeckillUser;

/**
 * @author initiald0824
 * @date 2019/6/20 16:02
 */
public class UserContext {

    private static ThreadLocal<SeckillUser> userHolder = new ThreadLocal<>();

    public static SeckillUser getUser() {
        return userHolder.get();
    }

    public static void setUser(SeckillUser user) {
        userHolder.set(user);
    }
}
