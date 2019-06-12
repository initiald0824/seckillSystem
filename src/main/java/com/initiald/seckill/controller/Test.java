package com.initiald.seckill.controller;

import com.initiald.seckill.domain.SeckillUser;
import com.initiald.seckill.domain.User;
import com.initiald.seckill.rabbitmq.MqSender;
import com.initiald.seckill.redis.RedisService;
import com.initiald.seckill.redis.UserKey;
import com.initiald.seckill.result.Result;
import com.initiald.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Autowired
    private MqSender mqSender;

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

    @RequestMapping("/mq")
    public Result mq() {
        mqSender.send("mq send test");
        return Result.success("mq test");
    }

    @RequestMapping("/mq/topic")
    public Result mqTopic() {
        mqSender.sendTopic("mq send topic test");
        return Result.success("mq send topic test");
    }

    @RequestMapping("/mq/fanout")
    public Result mqFanout() {
        mqSender.sendFanout("mq send fanout test");
        return Result.success("mq send fanout test");
    }

    @RequestMapping("/mq/header")
    public Result mqHeader() {
        mqSender.sendHeader("mq send header test");
        return Result.success("mq send header test");
    }

}
