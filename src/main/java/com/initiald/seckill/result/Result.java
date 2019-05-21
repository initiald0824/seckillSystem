package com.initiald.seckill.result;

/**
 * @author initiald0824
 * @date 2019/4/4 15:33
 */
public class Result<T> {
    private int code;
    private String message;
    private T data;

    private Result(T data) {
        this.code = 0;
        this.message = "success";
        this.data = data;
    }

    private Result(CodeMsg codeMsg) {
        if (codeMsg == null) {
            return;
        }
        this.code = codeMsg.getCode();
        this.message = codeMsg.getMessage();
    }

    public static <E> Result success(E data) {
        return new Result<>(data);
    }

    public static Result error(CodeMsg codeMsg) {
        return new Result<>(codeMsg);
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
