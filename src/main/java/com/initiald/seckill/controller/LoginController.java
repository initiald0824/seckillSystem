package com.initiald.seckill.controller;

import com.initiald.seckill.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author initiald0824
 * @date 2019/4/16 17:11
 */

@RestController
public class LoginController {

    @Autowired
    private RedisService redisService;

    private static Logger log = LoggerFactory.getLogger(LoginController.class);


}
