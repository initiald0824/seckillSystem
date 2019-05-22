package com.initiald.seckill.service;

import com.initiald.seckill.dao.SeckillUserDao;
import com.initiald.seckill.domain.SeckillUser;
import com.initiald.seckill.result.CodeMsg;
import com.initiald.seckill.uitl.MD5Util;
import com.initiald.seckill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author initiald0824
 * @date 2019/5/22 20:10
 */
@Service
public class SeckillUserService {

    @Autowired
    private SeckillUserDao seckillUserDao;

    public SeckillUser getById(long id) {
        return seckillUserDao.getById(id);
    }

    public CodeMsg login(LoginVo loginVo) {
        if (loginVo == null) {
            return CodeMsg.SERVER_ERROR;
        }
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        SeckillUser user = seckillUserDao.getById(Long.parseLong(mobile));
        if (user == null) {
            return CodeMsg.MOBILE_NOT_EXIST;
        }
        String dbPass = user.getPassword();
        String salt = user.getSalt();
        if (dbPass.equals(MD5Util.md5(password + salt))) {
            return CodeMsg.SUCCESS;
        } else {
            return CodeMsg.PASSWORD_ERROR;
        }
    }
}
