package com.yjf;

import com.yjf.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

//@SpringBootTest
public  class XbApplicationTests {
    @Autowired
    UserService userService;
    @Test
    public   void test01(){
        List<Map<String, String>> list = userService.selectCurrentDayCount();
        List<Map<String, String>> mapList = initList();
        for (Map<String, String> map : mapList) {
            for (Map<String, String> stringMap : list) {
                    if (map.get("registerTime").equals(stringMap.get("registerTime"))){
                        map.put("count",stringMap.get("count"));
                    }
            }
        }
        System.out.println(mapList);



    }


    public  List<Map<String, String>> initList(){
        ArrayList<Map<String, String>> list = new ArrayList<>();
        DateFormat dateFormat= new SimpleDateFormat("YYYY-MM-dd");
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 9; i++) {
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE,-i);
            String registerTime = dateFormat.format(calendar.getTime());
            HashMap<String, String> map = new HashMap<>();
            map.put("registerTime",registerTime);
            map.put("count","0");
            list.add(map);
        }
        return list;

    }





}
