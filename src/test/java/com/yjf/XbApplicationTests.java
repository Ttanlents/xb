package com.yjf;

import com.yjf.dao.MeetingDao;
import com.yjf.entity.Meeting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public  class XbApplicationTests {
  @Autowired
  MeetingDao meetingDao;
    @Test
    public   void test01(){
      List<Meeting> meetings = meetingDao.selectPage("关于","2");
      System.out.println(meetings);
    }

}
