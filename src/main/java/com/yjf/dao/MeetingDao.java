package com.yjf.dao;

import com.yjf.entity.Meeting;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/11/20 11:59
 * @Description
 */
@org.apache.ibatis.annotations.Mapper
public interface MeetingDao extends Mapper<Meeting> {
   List<Meeting> selectPage(@Param("title") String title, @Param("status") String status);

   @Override
   @Select("SELECT\n" +
           "\tmeeting.id,\n" +
           "\tmeeting.dept_id,\n" +
           "\tmeeting.title,\n" +
           "\tmeeting.content,\n" +
           "\tmeeting.publish_date,\n" +
           "\tmeeting.start_time,\n" +
           "\tmeeting.end_time,\n" +
           "\tmeeting.`status`,\n" +
           "\tmeeting.make_user,\n" +
           "\tdept.`name` dept_name\n" +
           "FROM\n" +
           "\tmeeting,\n" +
           "\tdept \n" +
           "WHERE\n" +
           "\tmeeting.dept_id = dept.id")
   List<Meeting> selectAll();

   @Insert("insert into meeting_join values(#{userId},#{meetingId})")
   int saveMeetingJoin(@Param("meetingId") Integer meetingId,@Param("userId") Integer userId);
}
