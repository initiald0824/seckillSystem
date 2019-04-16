package com.initiald.seckill.service;

import com.initiald.seckill.dao.UserDao;
import com.initiald.seckill.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author initiald0824
 * @date 2019/4/16 9:23
 */

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getById(int id) {
        return userDao.getById(id);
    }
}
