package com.yjf.service;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.Meeting;

import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/11/20 12:53
 * @Description
 */
public interface MeetingService extends BaseService<Meeting,Integer>{

   PageInfo<Meeting> selectPage(Integer pageNum,Integer pageSize,String title, String status);

   Meeting selectById(Integer id);

   List<Integer> selectActuallyJoin(Integer meetId);

   int  changeMeetingJoin(Integer uId,Integer mId,Boolean flag);

   void changeMeetingStatus();

   int RecentMeetCount();

   List<Map<String,String>> selectCurrentDayCount();



}
