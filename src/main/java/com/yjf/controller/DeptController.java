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
    DeptService deptService;

    @RequestMapping("getAllDept")
    @ResponseBody
    public Result getAllDept(){
        Result result = new Result();
        List<Dept> deptList = deptService.getAllDept();
        result.setObj(deptList);
        return result;
    }


}
