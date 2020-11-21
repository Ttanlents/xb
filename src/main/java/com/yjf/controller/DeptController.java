package com.yjf.controller;

import com.yjf.entity.Dept;
import com.yjf.entity.Result;
import com.yjf.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/11/20 11:11
 * @Description
 */
@Controller
@RequestMapping("dept")
public class DeptController {
    @Autowired
    private  DeptService deptService;

    /**
     *@Description TODO:查询全部 部门的信息，包括其拥有成员
     *@author 余俊锋
     *@date 2020/11/21 13:26
     *@params
     *@return com.yjf.entity.Result
     */
    @RequestMapping("getAllDept")
    @ResponseBody
    public Result getAllDept(){
        Result result = new Result();
        List<Dept> deptList = deptService.getAllDept();
        result.setObj(deptList);
        return result;
    }


}
