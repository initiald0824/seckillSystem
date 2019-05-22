package com.initiald.seckill.controller;

import com.initiald.seckill.redis.RedisService;
import com.initiald.seckill.result.CodeMsg;
import com.initiald.seckill.result.Result;
import com.initiald.seckill.service.SeckillUserService;
import com.initiald.seckill.uitl.ValidatorUtil;
import com.initiald.seckill.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author initiald0824
 * @date 2019/4/16 17:11
 */

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private SeckillUserService userService;

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/login")
    public Result login(LoginVo loginVo) {
        log.info(loginVo.toString());
        // 登录
        CodeMsg cm = userService.login(loginVo);
        if (cm.getCode() == 0) {
            return Result.success(CodeMsg.SUCCESS);
        } else {
            return Result.error(cm);
        }
    }
}
