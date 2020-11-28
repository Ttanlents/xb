package com.yjf.controller;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.Result;
import com.yjf.entity.User;
import com.yjf.service.UserService;
import com.yjf.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

/**
 * @author 余俊锋
 * @date 2020/11/18 13:22
 * @Description
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    Environment environment;

    /**
     * @param pageSize
     * @param username
     * @return com.yjf.entity.Result
     * @Description TODO:用户列表 分页查询
     * @author 余俊锋
     * @date 2020/11/21 13:35
     * @params pageNum
     */
    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    @ResponseBody
    public Result selectPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize, String username) {
        Result result = new Result();
        PageInfo<User> pageInfo = userService.selectPage(pageNum, pageSize, username);
        result.setObj(pageInfo);
        return result;
    }

    @RequestMapping("toDetail")
    public String toDetail() {
        return "/html/user_detail.html";
    }

    /**
     * @param focusId
     * @param flag
     * @return com.yjf.entity.Result
     * @Description TODO:关注  或  取消关注
     * @author 余俊锋
     * @date 2020/11/21 13:34
     * @params loginUserId
     */
    @RequestMapping(value = "changeFocus", method = RequestMethod.PUT)
    @ResponseBody
    public Result changeFocus(Integer loginUserId, Integer focusId, Boolean flag) {
        Result result = new Result();
        if (flag != null) {
            if (flag) {
                //加关注
                userService.addFocus(loginUserId, focusId);
                result.setMsg("关注成功");
            } else {
                //取消关注
                userService.chancelFocus(loginUserId, focusId);
                result.setMsg("取关成功");
            }
            return result;
        }
        result.setSuccess(false);
        result.setMsg("操作失败");
        return result;
    }

    /**
     * @param pageSize
     * @param userId
     * @return com.yjf.entity.Result
     * @Description TODO: 我关注的用户
     * @author 余俊锋
     * @date 2020/11/20 9:44
     * @params pageNum
     */
    @RequestMapping(value = "getFocusUsers/{pageNum}/{pageSize}")
    @ResponseBody
    public Result getFocusUsers(@PathVariable Integer pageNum, @PathVariable Integer pageSize, Integer userId) {
        Result result = new Result();
        PageInfo<User> pageInfo = userService.selectFocusUsers(pageNum, pageSize, userId);
        result.setObj(pageInfo);
        return result;
    }

    /**
     * @return com.yjf.entity.Result
     * @Description TODO:获取当前登录用户的信息
     * @author 余俊锋
     * @date 2020/11/21 13:34
     * @params userId
     */
    @RequestMapping(value = "initLoginUser")
    @ResponseBody
    public Result initLoginUser(Integer userId) {
        Result result = new Result();
        User user = new User();
        user.setId(userId);
        User loginUser = userService.selectUserById(userId);
        loginUser.setPassword(null);
        result.setObj(loginUser);
        return result;
    }

    @RequestMapping(value = "getCode")
    @ResponseBody
    public Result getCode(String emailName, HttpSession session) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(new Random().nextInt(10));
        }
        String code = sb.toString();
        session.setAttribute("changePassword", code);
        EmailUtil.sendEmail(emailName, code);
        return new Result(true, "发送验证码成功", null);
    }

    @RequestMapping(value = "changePassword", method = RequestMethod.PUT)
    @ResponseBody
    public Result changePassword(String emailName, String code, String password, HttpSession session) {
        Result result = new Result();
        User user = new User();
        user.setEmail(emailName);
        User one = userService.selectOne(user);
        String sessionCode = (String) session.getAttribute("changePassword");
        if (one != null && sessionCode.equals(code)) {
            one.setPassword(password);
            int i = userService.updateByPrimaryKeySelective(one);
            if (i > 0) {
                result.setMsg("修改密码成功");
                return result;
            }
        }
        result.setSuccess(false);
        result.setMsg("修改失败");
        return result;
    }
    /**
     *@Description TODO:上传头像到nginx服务器
     *@author 余俊锋
     *@date 2020/11/28 16:48
     *@params file
     *@return com.yjf.entity.Result
     */


    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public Result getCode(MultipartFile file) throws IOException {
        String picName = UUID.randomUUID().toString().replace("-", "");
        String prefix = environment.getProperty("uploads.file.prefix");
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.indexOf("."));
        File file1 = new File(prefix + picName + suffix);
        file.transferTo(file1);
        String url = "http://localhost:80/uploads/" + picName + suffix;
        return new Result(true, "上传成功", url);
    }

    /**
     *@Description TODO:保存个人中心信息
     *@author 余俊锋
     *@date 2020/11/28 16:48
     *@params user
     *@return com.yjf.entity.Result
     */


    @RequestMapping(value = "doSave", method = RequestMethod.POST)
    @ResponseBody
    public Result doSave(@RequestBody User user) {
        System.out.println(user);
        userService.updateByPrimaryKeySelective(user);
        return new Result(true,"保存成功",null);
    }

    @RequestMapping(value = "updateUserLook", method = RequestMethod.PUT)
    @ResponseBody
    public Result updateUserLook(Integer id) {
        userService.updateUserLook(id);
        return new Result(true,"被看次数+1",null);
    }



}
