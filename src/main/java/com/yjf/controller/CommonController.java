package com.yjf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 余俊锋
 * @date 2020/11/18 13:07
 * @Description
 */
@Controller
public class CommonController {

    @RequestMapping(value = "getNavBar")
    public String getNavBar(){
        return "/common/navbar.html";
    }

    @RequestMapping(value = "getSideBar")
    public String getSideBar(){
        return "/common/sidebar.html";
    }
}
