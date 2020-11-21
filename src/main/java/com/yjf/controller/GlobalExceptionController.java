package com.yjf.controller;

import com.baomidou.kaptcha.exception.KaptchaException;
import com.baomidou.kaptcha.exception.KaptchaIncorrectException;
import com.baomidou.kaptcha.exception.KaptchaNotFoundException;
import com.baomidou.kaptcha.exception.KaptchaTimeoutException;
import com.yjf.entity.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 余俊锋
 * @date 2020/11/3 14:42
 * @Description
 */
@ControllerAdvice
public class GlobalExceptionController {


    /**
     *@Description TODO:验证码异常处理
     *@author 余俊锋
     *@date 2020/11/21 13:28
     *@params kaptchaException
     *@return com.yjf.entity.Result
     */
    @ExceptionHandler(value = KaptchaException.class)
    @ResponseBody
    public Result kaptchaExceptionHandler(KaptchaException kaptchaException) {
        if (kaptchaException instanceof KaptchaIncorrectException) {
            return new Result(false,"验证码不正确",null);
        } else if (kaptchaException instanceof KaptchaNotFoundException) {
            return new Result(false,"验证码未找",null);
        } else if (kaptchaException instanceof KaptchaTimeoutException) {
            return new Result(false,"验证码过期",null);
        } else {
            return new Result(false,"验证码渲染失败",null);
        }

    }

    /**
     *@Description TODO:全局运行时异常处理
     *@author 余俊锋
     *@date 2020/11/21 13:28
     *@params e
     *@return com.yjf.entity.Result
     */
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public Result allExceptionHandler(Exception e) {
            return new Result(false,e.getMessage(),null);
        }


}
