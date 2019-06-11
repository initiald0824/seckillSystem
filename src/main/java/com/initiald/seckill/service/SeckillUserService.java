package com.initiald.seckill.service;

import com.initiald.seckill.controller.LoginController;
import com.initiald.seckill.dao.SeckillUserDao;
import com.initiald.seckill.domain.SeckillUser;
import com.initiald.seckill.exception.GlobalException;
import com.initiald.seckill.redis.RedisService;
import com.initiald.seckill.redis.SeckillUserKey;
import com.initiald.seckill.result.CodeMsg;
import com.initiald.seckill.uitl.MD5Util;
import com.initiald.seckill.uitl.TokenUtil;
import com.initiald.seckill.uitl.UUIDUtil;
import com.initiald.seckill.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.annotation.XmlElementDecl;

/**
 * @author initiald0824
 * @date 2019/5/22 20:10
 */
@Service
public class SeckillUserService {

    @Autowired
    private SeckillUserDao seckillUserDao;

    @Autowired
    private RedisService redisService;

    private static Logger log = LoggerFactory.getLogger(SeckillUserService.class);

    public static final String COOK1_NAME_TOKEN = "token";

    public SeckillUser getById(long id) {
        SeckillUser user = redisService.get(SeckillUserKey.getById, ""+id, SeckillUser.class);
        if (user != null) {
            return user;
        }
        user = seckillUserDao.getById(id);
        if (user != null) {
            redisService.set(SeckillUserKey.getById, ""+id, user);
        }
        return user;
    }

    public boolean updatePassword(String token, long id, String passwordNew) {
        // 取user对象
        SeckillUser user = getById(id);
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        // 更新数据库
        SeckillUser toBeUpdate = new SeckillUser();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.inputPassToDBPass(passwordNew, user.getSalt()));
        seckillUserDao.update(toBeUpdate);
        // 处理缓存
        redisService.delete(SeckillUserKey.getById, ""+id);
        user.setPassword(toBeUpdate.getPassword());
        redisService.set(SeckillUserKey.token, token, user);
        return true;
    }

    public void generateCookie(String token, HttpServletResponse response, SeckillUser user) {
        redisService.set(SeckillUserKey.token, token, user);
        Cookie cookie = new Cookie(COOK1_NAME_TOKEN, token);
        cookie.setMaxAge(SeckillUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 登录
     * @param response
     * @param loginVo
     * @return
     */
    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        SeckillUser user = seckillUserDao.getById(Long.parseLong(mobile));
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        String dbPass = user.getPassword();
        String salt = user.getSalt();
        if (dbPass.equals(MD5Util.md5(password + salt))) {
            // 生成cookie
            String token = TokenUtil.getToken(user);
            generateCookie(token, response, user);
            log.info("login success");
            return true;
        } else {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
    }

    public SeckillUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }

        SeckillUser user = redisService.get(SeckillUserKey.token, token, SeckillUser.class);
        if (user != null) {
            generateCookie(token, response, user);
        }
        return user;
    }

    /**
     * 刷新token
     * @param request
     * @param response
     * @return
     */
    public boolean authorization(HttpServletRequest request, HttpServletResponse response) {
        SeckillUser user = (SeckillUser) request.getAttribute("user");
        String token = (String) request.getAttribute("token");
        generateCookie(token, response, user);
        return true;
    }
}
