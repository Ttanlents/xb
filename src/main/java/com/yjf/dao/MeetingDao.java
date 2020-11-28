package com.yjf.dao;

import com.yjf.entity.Meeting;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

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
    int saveMeetingJoin(@Param("meetingId") Integer meetingId, @Param("userId") Integer userId);

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
            "\tmeeting.dept_id = dept.id and meeting.id=#{id}")
    Meeting selectById(Integer id);

    @Select("select meeting_join.u_id from meeting_join where meeting_join.m_id=#{meetId}")
    List<Integer> selectActuallyJoin(Integer meetId);


    @Insert("insert into meeting_join values(#{uId},#{mId})")
    int insertMeetingJoin(@Param("uId")Integer uId,@Param("mId")Integer mId);

    @Delete("delete from meeting_join where u_id=#{uId} and m_id=#{mId}")
    int deleteMeetingJoin(@Param("uId")Integer uId,@Param("mId")Integer mId);

    @Select("SELECT count(1) FROM `meeting` where publish_date BETWEEN DATE_SUB(NOW(),interval 8 day) and now()")
    int selectRecentMeetCount();

    @Select("SELECT count(1) count,date_format(publish_date, '%Y-%m-%d') register_time FROM `meeting`  where DATEDIFF(now(),publish_date)<9  group by date_format(publish_date, '%m-%d')")
    List<Map<String,String>> selectCurrentDayCount();
}
