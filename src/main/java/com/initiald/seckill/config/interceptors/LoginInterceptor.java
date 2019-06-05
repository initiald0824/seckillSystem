package com.initiald.seckill.config.interceptors;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.initiald.seckill.config.annotation.PassToken;
import com.initiald.seckill.config.annotation.UserLoginToken;
import com.initiald.seckill.domain.SeckillUser;
import com.initiald.seckill.exception.GlobalException;
import com.initiald.seckill.result.CodeMsg;
import com.initiald.seckill.service.SeckillUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author initiald0824
 * @date 2019/5/27 19:14
 * @desp 检查解析请求携带的token是否合法
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private SeckillUserService userService;

    @Autowired
    private SeckillUserService seckillUserService;

    private static Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle");
        String token = request.getHeader("authorization");
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        // 检查是否有PassToken注释，有则跳过验证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        // 检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                if (token == null) {
                    throw new GlobalException(CodeMsg.NO_TOKEN);
                }
                // 获取token中的user_id
                String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException e) {
                    throw new GlobalException(CodeMsg.WRONG_TOKEN);
                }
                SeckillUser user = userService.getById(Long.valueOf(userId));
                if (user == null) {
                    throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
                }
                request.setAttribute("user", user);
                request.setAttribute("token", token);
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new GlobalException(CodeMsg.WRONG_TOKEN);
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        SeckillUser user = (SeckillUser) request.getAttribute("user");
        String token = (String) request.getAttribute("token");
        seckillUserService.generateCookie(token, response, user);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
