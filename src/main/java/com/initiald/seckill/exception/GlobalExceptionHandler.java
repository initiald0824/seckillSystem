package com.initiald.seckill.exception;

import com.initiald.seckill.result.CodeMsg;
import com.initiald.seckill.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author initiald0824
 * @date 2019/5/23 20:05
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(HttpServletRequest request, Exception e) {
        if (e instanceof GlobalException) {
            GlobalException ex = (GlobalException) e;
            return Result.error(ex.getCodeMsg());
        } else
            if (e instanceof BindException) {
            BindException ex = (BindException)e;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String message = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(message));
        } else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
