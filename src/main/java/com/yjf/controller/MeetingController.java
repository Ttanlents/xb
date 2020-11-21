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

import javax.servlet.http.HttpSession;
import java.util.*;

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

    /**
     *@Description TODO:分页查询会议
     *@author 余俊锋
     *@date 2020/11/21 11:28
     *@params pageNum
     * @param pageSize
     * @param title
     * @param status
     *@return com.yjf.entity.Result
     */
    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    @ResponseBody
    public Result selectPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize, String title, String status) {
        Result result = new Result();
        PageInfo<Meeting> pageInfo = meetingService.selectPage(pageNum, pageSize, title, status);
        result.setObj(pageInfo);
        return result;
    }
/**
 *@Description TODO:查询所有的部门
 *@author 余俊锋
 *@date 2020/11/21 11:28
 *@params
 *@return com.yjf.entity.Result
 */
    @RequestMapping("selectAllDept")
    @ResponseBody
    public Result selectAllDept() {
        Result result = new Result();
        List<Dept> deptList = deptService.selectAll();
        result.setObj(deptList);
        return result;
    }
/**
 *@Description TODO:查询该id 部门  拥有的人
 *@author 余俊锋
 *@date 2020/11/21 11:29
 *@params deptId
 *@return com.yjf.entity.Result
 */
    @RequestMapping("getDeptUsers")
    @ResponseBody
    public Result getDeptUsers(Integer deptId) {
        Result result = new Result();
        List<User> userList = userService.selectUsersByDeptId(deptId);
        result.setObj(userList);
        return result;
    }

    /**
     *@Description TODO:发布会议
     *@author 余俊锋
     *@date 2020/11/21 11:29
     *@params map
     *@return com.yjf.entity.Result
     */
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
    /**
     *@Description TODO:获取会议详情
     *@author 余俊锋
     *@date 2020/11/21 11:30
     *@params id
     * @param session
     *@return com.yjf.entity.Result
     */
    @RequestMapping(value = "getDetail")
    @ResponseBody
    public Result getDetail(Integer id, HttpSession session) {
        Result result = new Result();
        Map<String,Object> map=new HashMap<>();
        ArrayList<Integer> needJoin = new ArrayList<>();  //需要参加会议的人
        Meeting meeting = meetingService.selectById(id);
        String makeUser = meeting.getMakeUser();
       if (makeUser!=null&&!makeUser.equals("")){
           for (String s : makeUser.split(",")) {
               needJoin.add(Integer.parseInt(s));
           }
       }
        List<Integer> actuallyJoin = meetingService.selectActuallyJoin(id);
        Boolean flag=true;
        //1.判断是否需要参加
        //2.如果需要参加，判断是否已经参加
        User loginUser =(User) session.getAttribute("loginUser");
        if (needJoin.contains(loginUser.getId())){
            //需要参加
           if (!actuallyJoin.contains(loginUser.getId())){
               //还没参加
                map.put("flag",1);
           }else {
               //已经参加
               map.put("flag",2);
           }
        }else {
            //不需要参加
            map.put("flag",3);
        }
        //应到
        int should = needJoin.size();
        //实到
        int actually = actuallyJoin.size();
        //未到
        int notJoin = should - actually;
        map.put("should", should);
        map.put("actually", actually);
        map.put("notJoin", notJoin);
        map.put("meet",meeting);
        result.setObj(map);
        return result;
    }

    /**
     *@Description TODO:参加  或  取消会议
     *@author 余俊锋
     *@date 2020/11/21 13:33
     *@params id
     * @param flag
     * @param session
     *@return com.yjf.entity.Result
     */
    @RequestMapping(value = "changeJoinMeet", method = RequestMethod.PUT)
    @ResponseBody
    public Result changeJoinMeet(Integer id,Boolean flag,HttpSession session) {
        Result result = new Result();
        User loginUser =(User) session.getAttribute("loginUser");
        Meeting meeting = meetingService.selectById(id);
        int status = meeting.getStatus();
        if (status==0){
            int i = meetingService.changeMeetingJoin(loginUser.getId(), id, flag);
            if (i>0&&flag){
                result.setMsg("参加会议成功");
            }else {
                result.setMsg("取消参加成功");
            }
        }else {
            result.setSuccess(false);
            if (status==1){
                result.setMsg("操作失败，会议已经开始了");
            }else if (status==2){
                result.setMsg("操作失败，会议已经结束");
            }
        }
        return result;
    }


}
