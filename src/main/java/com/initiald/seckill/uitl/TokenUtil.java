package com.initiald.seckill.uitl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.initiald.seckill.domain.SeckillUser;

/**
 * @author initiald0824
 * @date 2019/5/31 16:50
 */
public class TokenUtil {
    public static String getToken(SeckillUser user) {
        return JWT.create().withAudience(user.getId().toString()).sign(Algorithm.HMAC256(user.getPassword()));
    }
}
