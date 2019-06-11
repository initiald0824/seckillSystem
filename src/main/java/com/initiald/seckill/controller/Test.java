package com.initiald.seckill.controller;

import com.initiald.seckill.domain.SeckillUser;
import com.initiald.seckill.domain.User;
import com.initiald.seckill.redis.RedisService;
import com.initiald.seckill.redis.UserKey;
import com.initiald.seckill.result.Result;
import com.initiald.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author initiald0824
 * @date 2019/4/14 22:45
 */
@RestController
public class Test {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "/api/test", method = RequestMethod.GET)
    public Result test(SeckillUser user) {
        return Result.success(user);
    }

    @RequestMapping(value = "/user/info", method = RequestMethod.GET)
    public Result info(SeckillUser user) {
        return Result.success(user);
    }

    @RequestMapping("/redis/get")
    public Result redisGet() {
        User user = redisService.get(UserKey.getById, "key1", User.class);
        return Result.success(user);
    }

    @RequestMapping("/redis/set")
    public Result redisSet() {
        User user = new User();
        user.setId(23);
        user.setName("test");
        boolean ret = redisService.set(UserKey.getById,"key2", user);
        User getUser = redisService.get(UserKey.getById, "key2", User.class);
        return Result.success(getUser);
    }
}
