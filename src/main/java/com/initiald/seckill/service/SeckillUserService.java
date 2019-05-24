package com.initiald.seckill.service;

import com.initiald.seckill.dao.SeckillUserDao;
import com.initiald.seckill.domain.SeckillUser;
import com.initiald.seckill.exception.GlobalException;
import com.initiald.seckill.redis.RedisService;
import com.initiald.seckill.redis.SeckillUserKey;
import com.initiald.seckill.result.CodeMsg;
import com.initiald.seckill.uitl.MD5Util;
import com.initiald.seckill.uitl.UUIDUtil;
import com.initiald.seckill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
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


    private static final String COOK1_NAME_TOKEN = "token";

    public SeckillUser getById(long id) {
        return seckillUserDao.getById(id);
    }

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
            String token = UUIDUtil.uuid();
            redisService.set(SeckillUserKey.token, token, user);
            Cookie cookie = new Cookie(COOK1_NAME_TOKEN, token);
            cookie.setMaxAge(SeckillUserKey.token.expireSeconds());
            cookie.setPath("/");
            response.addCookie(cookie);
            return true;
        } else {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
    }
}
