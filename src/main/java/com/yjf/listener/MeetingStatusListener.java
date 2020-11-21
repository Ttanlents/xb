package com.yjf.listener;

import com.yjf.timerTask.MeetingTimerTask;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Date;
import java.util.Timer;

/**
 * @author 余俊锋
 * @date 2020/11/21 15:13
 * @Description
 */
@WebListener
public class MeetingStatusListener implements ServletContextListener {

    @Autowired
    private MeetingTimerTask meetingTimerTask;
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Timer timer = new Timer();
        timer.schedule(meetingTimerTask,new Date(),300*1000);
        System.out.println("application初始化完成");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
