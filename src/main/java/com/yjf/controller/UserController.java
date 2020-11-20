package com.yjf.controller;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.Result;
import com.yjf.entity.User;
import com.yjf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 余俊锋
 * @date 2020/11/18 13:22
 * @Description
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    @ResponseBody
    public Result selectPage(@PathVariable  Integer pageNum,@PathVariable Integer pageSize, String username) {
        Result result = new Result();
        PageInfo<User> pageInfo = userService.selectPage(pageNum, pageSize, username);
        result.setObj(pageInfo);
        return result;
    }

    @RequestMapping("toDetail")
    public String toDetail() {
        return "/html/user_detail.html";
    }

    @RequestMapping(value = "changeFocus",method = RequestMethod.PUT)
    @ResponseBody
    public Result changeFocus(Integer loginUserId,Integer focusId,Boolean flag){
        Result result = new Result();
        if (flag!=null){
           if (flag){
               //加关注
               userService.addFocus(loginUserId, focusId);
               result.setMsg("关注成功");
           }else {
               //取消关注
               userService.chancelFocus(loginUserId, focusId);
               result.setMsg("取关成功");
           }
           return result;
        }
        result.setSuccess(false);
        result.setMsg("操作失败");
        return  result;
    }

    /**
     *@Description TODO: 我关注的用户
     *@author 余俊锋
     *@date 2020/11/20 9:44
     *@params pageNum
     * @param pageSize
     * @param userId
     *@return com.yjf.entity.Result
     */
    @RequestMapping(value = "getFocusUsers/{pageNum}/{pageSize}")
    @ResponseBody
    public Result getFocusUsers(@PathVariable Integer pageNum,@PathVariable  Integer pageSize,Integer userId){
        Result result = new Result();
        PageInfo<User> pageInfo = userService.selectFocusUsers(pageNum, pageSize, userId);
        result.setObj(pageInfo);
        return result;
    }

    @RequestMapping(value = "initLoginUser")
    @ResponseBody
    public Result initLoginUser(Integer userId){
        Result result = new Result();
        User user = new User();
        user.setId(userId);
        User loginUser = userService.selectUserById(userId);
        loginUser.setPassword(null);
        result.setObj(loginUser);
        return result;
    }



}
