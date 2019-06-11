package com.initiald.seckill.result;


/**
 * @author initiald0824
 * @date 2019/4/4 15:34
 */
public class CodeMsg {
    private int code;
    private String message;

    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500, "server error");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500100, "empty mobile");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500101, "error mobile");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500102, "mobile not exist");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500103, "password error");

    public static CodeMsg BIND_ERROR = new CodeMsg(500110, "参数校验异常: %s");

    public static CodeMsg NO_TOKEN = new CodeMsg(500201, "无Token");

    public static CodeMsg WRONG_TOKEN = new CodeMsg(500202, "错误的Token");

    public static CodeMsg SECKILL_OVER = new CodeMsg(500500, "商品秒杀完毕");

    public static CodeMsg SECKILL_REPEATE = new CodeMsg(500501, "不能重复秒杀");

    public static CodeMsg ORDER_NOT_EXIST = new CodeMsg(500502, "订单不存在");


    private CodeMsg(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.message, args);
        return new CodeMsg(code, message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
