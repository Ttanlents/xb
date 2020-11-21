package com.yjf.controller;

import com.baomidou.kaptcha.Kaptcha;
import com.yjf.entity.Result;
import com.yjf.entity.User;
import com.yjf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author 余俊锋
 * @date 2020/11/17 19:32
 * @Description
 */
@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private Kaptcha kaptcha;

    /**
     * @return java.lang.String
     * @Description TODO:登录首页
     * @author 余俊锋
     * @date 2020/11/21 13:32
     * @params
     */
    @RequestMapping(value = "toIndex")
    public String toLogin() {
        return "/index.html";
    }

    /**
     * @param password
     * @param code
     * @param session
     * @return com.yjf.entity.Result
     * @Description TODO:校验登录
     * @author 余俊锋
     * @date 2020/11/21 13:32
     * @params username
     */
    @RequestMapping(value = "doLogin", method = RequestMethod.POST)
    @ResponseBody
    public Result doLogin(String username, String password, String code, HttpSession session) {
        String message = "";
        Result result = new Result();
        if (kaptcha.validate(code)) {
            User user = new User();
            User loginUser = userService.toLogin(username, password);
            if (loginUser != null) {
                session.setAttribute("loginUser", loginUser);
                loginUser.setPassword(null);
                result.setObj(loginUser);
                return new Result(true, "登录成功", loginUser);
            } else {
                message = "账号或密码错误";
            }
        } else {
            message = "验证码错误";
        }
        return new Result(false, message, null);
    }

    /**
     * @return com.yjf.entity.Result
     * @Description TODO:用户注册
     * @author 余俊锋
     * @date 2020/11/21 13:32
     * @params user
     */
    @RequestMapping(value = "doRegister", method = RequestMethod.POST)
    @ResponseBody
    public Result doRegister(@RequestBody User user) {
        Result result = new Result();
        int i = userService.insertSelective(user);
        if (i > 0) {
            result.setMsg("注册成功");

            return result;
        }
        result.setSuccess(false);
        result.setMsg("注册失败");
        return result;
    }

    /**
     * @return com.yjf.entity.Result
     * @Description TODO:校验   用户名是否已经存在
     * @author 余俊锋
     * @date 2020/11/21 13:33
     * @params username
     */
    @RequestMapping(value = "doCheckUsername")
    @ResponseBody
    public Result doCheckUsername(String username) {
        Result result = new Result();
        User user = new User();
        user.setUsername(username);
        User existUser = userService.selectOne(user);
        if (existUser != null) {
            result.setMsg("用户名已存在");
            return result;
        }
        result.setSuccess(false);
        return result;
    }


}
