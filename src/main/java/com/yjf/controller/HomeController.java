package com.yjf.controller;

import com.yjf.entity.Result;
import com.yjf.service.ArticleService;
import com.yjf.service.MeetingService;
import com.yjf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 余俊锋
 * @date 2020/11/28 10:48
 * @Description
 */
@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private MeetingService meetingService;

    @ResponseBody
    @RequestMapping("/getRecentCount")
    public Result getRecentCount(){
        Map<String,Object> map=new HashMap<>();
        map.put("userCount",userService.RecentUserCount());
        map.put("articleCount",articleService.RecentArticleCount());
        map.put("meetCount",meetingService.RecentMeetCount());
        return new Result(true,"查询成功",map);
    }

    @ResponseBody
    @RequestMapping("/getCurrentDayCount")
    public Result getCurrentDayCount(){
        Map<String, Object> map = new HashMap<>();  //总obj
        //用户
        List<Map<String, String>> list = userService.selectCurrentDayCount();
        List<Map<String, String>> mapList = initList(list);
        List<Object> objects=new ArrayList<>();
        for (Map<String, String> maps : mapList) {
            objects.add(maps.get("registerTime"));
            map.put("dateArr",objects);
        }
        objects=new ArrayList<>();
        for (Map<String, String> maps : mapList) {
            objects.add(maps.get("count"));
            map.put("userArr",objects);
        }
            //会议
        List<Map<String, String>> list1 = meetingService.selectCurrentDayCount();
        List<Map<String, String>> mapList1 = initList(list1);
        List<Object> objects1=new ArrayList<>();
        for (Map<String, String> maps : mapList1) {
            objects1.add(maps.get("count"));
            map.put("meetArr",objects1);
        }
        //文章
        List<Map<String, String>> list2 = articleService.selectCurrentDayCount();
        List<Map<String, String>> mapList2 = initList(list2);
        List<Object> objects2=new ArrayList<>();
        for (Map<String, String> maps : mapList2) {
            objects2.add(maps.get("count"));
            map.put("articleArr",objects2);
        }
        return new Result(true,"查询成功",map);
    }

    private List<Map<String, String>> initList( List<Map<String, String>> list){
        ArrayList<Map<String, String>> mapList = new ArrayList<>();
        DateFormat dateFormat= new SimpleDateFormat("YYYY-MM-dd");
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 9; i++) {
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE,-i);
            String registerTime = dateFormat.format(calendar.getTime());
            HashMap<String, String> map = new HashMap<>();
            map.put("registerTime",registerTime);
            map.put("count","0");
            mapList.add(map);
        }

        for (Map<String, String> map : mapList) {
            for (Map<String, String> stringMap : list) {
                if (map.get("registerTime").equals(stringMap.get("registerTime"))){
                    map.put("count",stringMap.get("count"));
                }
            }
        }
        return  mapList;
    }



}
