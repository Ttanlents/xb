package com.yjf.service;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.Meeting;

/**
 * @author 余俊锋
 * @date 2020/11/20 12:53
 * @Description
 */
public interface MeetingService extends BaseService<Meeting,Integer>{

   PageInfo<Meeting> selectPage(Integer pageNum,Integer pageSize,String title, String status);





}
