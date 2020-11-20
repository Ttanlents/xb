package com.yjf.service;

import com.yjf.entity.Dept;

import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/11/20 11:07
 * @Description
 */
public interface DeptService extends BaseService<Dept,Integer> {



    List<Dept> getAllDept();
}
