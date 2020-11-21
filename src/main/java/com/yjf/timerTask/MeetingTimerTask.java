package com.yjf.timerTask;

import com.yjf.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.TimerTask;

/**
 * @author 余俊锋
 * @date 2020/11/21 15:08
 * @Description
 */
@Component
public class MeetingTimerTask extends TimerTask {
    private boolean flag=false;  //避免还在执行期间  被下一个周期线程进入

    @Autowired
    private MeetingService meetingService;
    @Override
    public void run() {
        flag=true;
        meetingService.changeMeetingStatus();
        flag=false;
    }
}
