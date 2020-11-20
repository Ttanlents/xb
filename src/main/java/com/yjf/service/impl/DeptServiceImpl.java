package com.yjf.service.impl;

import com.yjf.dao.DeptDao;
import com.yjf.entity.Dept;
import com.yjf.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/11/20 11:08
 * @Description
 */
@Service
@Transactional
public class DeptServiceImpl extends BaseServiceImpl<Dept,Integer> implements DeptService {
    @Autowired
    DeptDao deptDao;
    @Override
    public List<Dept> getAllDept() {
     return   deptDao.selectAllDept();
    }
}
