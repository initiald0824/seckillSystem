package com.initiald.seckill.uitl;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author initiald0824
 * @date 2019/4/16 16:56
 */
public class MD5Util {

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }


    public static String inputPassToDBPass(String inputPass, String saltDB) {
        String str = inputPass +  saltDB;
        return md5(str);
    }
}
