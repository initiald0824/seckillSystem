package com.initiald.seckill.controller;

import com.initiald.seckill.config.annotation.UserLoginToken;
import com.initiald.seckill.redis.RedisService;
import com.initiald.seckill.result.CodeMsg;
import com.initiald.seckill.result.Result;
import com.initiald.seckill.service.SeckillUserService;
import com.initiald.seckill.uitl.ValidatorUtil;
import com.initiald.seckill.vo.LoginVo;
import com.sun.deploy.net.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    /**
     * 用户名：110 密码：test
     * @param response
     * @param loginVo
     * @return
     */
    @RequestMapping("/login")
    public Result login(HttpServletResponse response, LoginVo loginVo) {
        log.info(loginVo.toString());
        // 登录
        return Result.success(userService.login(response, loginVo));
    }

    @UserLoginToken
    @RequestMapping("/authorization")
    public Result authorization(HttpServletRequest request, HttpServletResponse response) {
        log.info("刷新token");
        return Result.success(userService.authorization(request, response));
    }
}
