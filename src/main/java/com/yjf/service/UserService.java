package com.yjf.service;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/11/17 20:08
 * @Description
 */
public interface UserService extends BaseService<User,Integer>{

    PageInfo<User> selectPage(Integer pageNum,Integer pageSize,String username);

    User toLogin(String username,String password);

    int addFocus(Integer loginUserId,Integer focusId);
    int chancelFocus(Integer loginUserId,Integer focusId);

    PageInfo<User> selectFocusUsers(Integer pageNum,Integer pageSize,Integer userId);

    List<User> selectUsersByArticleId(Integer userId, Integer articleId);

    User selectUserById(Integer userId);

    List<User> selectUsersByDeptId(Integer deptId);

    int RecentUserCount();

    List<Map<String,String>> selectCurrentDayCount();

    void updateUserLook(Integer id);
}
