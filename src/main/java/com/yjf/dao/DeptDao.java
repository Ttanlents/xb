package com.yjf.dao;

import com.yjf.entity.Dept;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/11/20 10:48
 * @Description
 */
@org.apache.ibatis.annotations.Mapper
public interface DeptDao extends Mapper<Dept> {

    List<Dept> selectAllDept();





}
