package com.yjf.controller;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.Dept;
import com.yjf.entity.Meeting;
import com.yjf.entity.Result;
import com.yjf.entity.User;
import com.yjf.service.DeptService;
import com.yjf.service.MeetingService;
import com.yjf.service.UserService;
import com.yjf.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/11/20 12:51
 * @Description
 */
@Controller
@RequestMapping("meeting")
public class MeetingController {
    @Autowired
    private MeetingService meetingService;

    @Autowired
    private DeptService deptService;
    @Autowired
    private UserService userService;

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    @ResponseBody
    public Result selectPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize, String title, String status) {
        Result result = new Result();
        PageInfo<Meeting> pageInfo = meetingService.selectPage(pageNum, pageSize, title, status);
        result.setObj(pageInfo);
        return result;
    }

    @RequestMapping("selectAllDept")
    @ResponseBody
    public Result selectAllDept() {
        Result result = new Result();
        List<Dept> deptList = deptService.selectAll();
        result.setObj(deptList);
        return result;
    }

    @RequestMapping("getDeptUsers")
    @ResponseBody
    public Result getDeptUsers(Integer deptId) {
        Result result = new Result();
        List<User> userList = userService.selectUsersByDeptId(deptId);
        result.setObj(userList);
        return result;
    }

    @RequestMapping(value = "doSave", method = RequestMethod.POST)
    @ResponseBody
    public Result doSave(@RequestBody Map<String,Object> map) {
        Result result = new Result();
        Object obj = map.get("meeting");
        String json = JsonUtils.pojoToJson(obj);
        Meeting meeting = JsonUtils.jsonToPojo(json, Meeting.class);
        String makeUsers =(String) map.get("makeUsers");
        meeting.setPublishDate(new Date());
        meeting.setMakeUser(makeUsers);
        meeting.setStatus(0);
        int i = meetingService.insertSelective(meeting);
        if (i>0){
            result.setMsg("发布成功");
            return result;
        }
        result.setSuccess(false);
        result.setMsg("发布失败");
        return result;
    }
}
