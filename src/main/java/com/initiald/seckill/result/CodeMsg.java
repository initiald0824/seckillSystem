package com.initiald.seckill.result;

import com.sun.org.apache.bcel.internal.classfile.Code;

/**
 * @author initiald0824
 * @date 2019/4/4 15:34
 */
public class CodeMsg {
    private int code;
    private String message;

    public CodeMsg SUCCESS = new CodeMsg(0, "success");
    public CodeMsg SERVER_ERROR = new CodeMsg(500, "server error");


    private CodeMsg(int code, String message) {
        this.code = code;
        this.message = message;
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
