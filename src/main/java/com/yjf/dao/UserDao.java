package com.yjf.dao;

import com.yjf.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/11/17 20:07
 * @Description
 */
@org.apache.ibatis.annotations.Mapper
public interface UserDao extends Mapper<User> {
    List<User> selectByUsername(String username);
    Integer getFocusCountByUserId(Integer id);
    List<Integer> selectFocusIds(Integer id);

    User selectByUsernameAndPassword(@Param("username") String username,@Param("password") String password);

    int saveUserFocus(@Param("loginUserId") Integer loginUserId,@Param("focusId") Integer focusId);
    int deleteUserFocus(@Param("loginUserId") Integer loginUserId,@Param("focusId") Integer focusId);

    List<User> selectFocusUsers(Integer userId);

    List<User> selectUsersByArticleId(@Param("userId") Integer userId, @Param("articleId") Integer articleId);

    User selectUserById(Integer userId);

    List<User> selectUsersByDeptId(Integer deptId);

}
