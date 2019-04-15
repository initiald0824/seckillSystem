package com.initiald.seckill.controller;

import com.initiald.seckill.result.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author initiald0824
 * @date 2019/4/14 22:45
 */
@RestController
public class Test {


    @RequestMapping(value = "/api/test", method = RequestMethod.GET)
    public Result test() {
        return Result.success("test");
    }

    @RequestMapping(value = "/redis/get", method = RequestMethod.GET)
    public Result redisGet() {
        return null;
    }

}
