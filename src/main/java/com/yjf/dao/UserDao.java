package com.yjf.dao;

import com.yjf.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/11/17 20:07
 * @Description
 */
@org.apache.ibatis.annotations.Mapper
public interface UserDao extends Mapper<User> {
    List<User> selectByUsername(String username);
    Integer getFocusCountByUserId(Integer id);
    Integer getBeenFocusCountByUserId(Integer id);
    List<Integer> selectFocusIds(Integer id);

    User selectByUsernameAndPassword(@Param("username") String username,@Param("password") String password);

    int saveUserFocus(@Param("loginUserId") Integer loginUserId,@Param("focusId") Integer focusId);
    int deleteUserFocus(@Param("loginUserId") Integer loginUserId,@Param("focusId") Integer focusId);

    List<User> selectFocusUsers(Integer userId);

    List<User> selectUsersByArticleId(@Param("userId") Integer userId, @Param("articleId") Integer articleId);

    User selectUserById(Integer userId);

    List<User> selectUsersByDeptId(Integer deptId);

    @Select("SELECT count(1) FROM `user` where register_time BETWEEN DATE_SUB(NOW(),interval 8 day) and now()")
    int selectRecentUserCount();

    @Select("SELECT count(1) count,date_format(register_time, '%Y-%m-%d') register_time FROM `user`  where DATEDIFF(now(),register_time)<9  group by date_format(register_time, '%m-%d')")
    List<Map<String,String>> selectCurrentDayCount();

    @Update("update user set user.look=user.look+#{count} where user.id=#{userId}")
    void updateUserLook(@Param("userId") Integer userId,@Param("count") Integer count);

}
