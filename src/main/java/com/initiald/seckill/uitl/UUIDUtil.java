package com.initiald.seckill.uitl;

import java.util.UUID;

/**
 * @author initiald0824
 * @date 2019/5/24 15:51
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
