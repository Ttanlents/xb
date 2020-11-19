package com.yjf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjf.dao.UserDao;
import com.yjf.entity.User;
import com.yjf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/11/17 20:11
 * @Description
 */
@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User,Integer> implements UserService {
    @Autowired
    UserDao userdao;
    @Override
    public PageInfo<User> selectPage(Integer pageNum,Integer pageSize,String username) {
        PageHelper.startPage(pageNum,pageSize);
        User user = new User();
        user.setUsername(username);
        List<User> userList = userdao.selectByUsername(username);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }

    @Override
    public User toLogin(String username, String password) {
       return userdao.selectByUsernameAndPassword(username,password);
    }

    @Override
    public int addFocus(Integer loginUserId, Integer focusId) {
       return userdao.saveUserFocus(loginUserId,focusId);
    }

    @Override
    public int chancelFocus(Integer loginUserId, Integer focusId) {
        return userdao.deleteUserFocus(loginUserId,focusId);
    }

    @Override
    public PageInfo<User> selectFocusUsers(Integer pageNum, Integer pageSize, Integer userId) {
        PageHelper.startPage(pageNum,pageSize);
        List<User> userList = userdao.selectFocusUsers(userId);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }


}
