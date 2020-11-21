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
    public List<Meeting> selectAll() {
        return meetingDao.selectAll();
    }
}
