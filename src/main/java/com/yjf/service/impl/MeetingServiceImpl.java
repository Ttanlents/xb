package com.yjf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjf.dao.MeetingDao;
import com.yjf.entity.Meeting;
import com.yjf.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/11/20 12:53
 * @Description
 */
@Service
@Transactional
public class MeetingServiceImpl extends BaseServiceImpl<Meeting, Integer> implements MeetingService {
    @Autowired
    MeetingDao meetingDao;

    @Override
    public PageInfo<Meeting> selectPage(Integer pageNum, Integer pageSize, String title, String status) {
        PageHelper.startPage(pageNum, pageSize);
        List<Meeting> meetingList = meetingDao.selectPage(title, status);
        PageInfo<Meeting> pageInfo = new PageInfo<>(meetingList);
        return pageInfo;
    }

    @Override
    public Meeting selectById(Integer id) {
        return meetingDao.selectById(id);
    }

    @Override
    public List<Integer> selectActuallyJoin(Integer meetId) {
        return meetingDao.selectActuallyJoin(meetId);
    }

    @Override
    public int changeMeetingJoin(Integer uId, Integer mId, Boolean flag) {
        if (flag) {
            return meetingDao.insertMeetingJoin(uId, mId);
        }
        return meetingDao.deleteMeetingJoin(uId, mId);
    }

    @Override
    public void changeMeetingStatus() {
        List<Meeting> meetings = meetingDao.selectAll();
        for (Meeting meeting : meetings) {
            long startTime = meeting.getStartTime().getTime();
            long endTime = meeting.getEndTime().getTime();
            long nowTime = System.currentTimeMillis();
            Integer status = meeting.getStatus();
            if (nowTime>startTime&&nowTime<endTime&&status!=1){
                meeting.setStatus(1);
                meetingDao.updateByPrimaryKeySelective(meeting);
                System.out.println("update:会议id:"+meeting.getId()+" status:"+1);
            }else if (nowTime>endTime&&status!=2){
                meeting.setStatus(2);
                meetingDao.updateByPrimaryKeySelective(meeting);
                System.out.println("update:会议id:"+meeting.getId()+" status:"+2);
            }else if(nowTime<startTime&&status!=0){
                meeting.setStatus(0);
                meetingDao.updateByPrimaryKeySelective(meeting);
                System.out.println("update:会议id:"+meeting.getId()+" status:"+0);
            }
        }
    }


    @Override
    public List<Meeting> selectAll() {
        return meetingDao.selectAll();
    }
}
