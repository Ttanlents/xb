package com.yjf.controller;

import com.yjf.entity.Article;
import com.yjf.entity.Result;
import com.yjf.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/11/19 10:47
 * @Description
 */
@Controller
@RequestMapping("article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    @ResponseBody
    public Result selectPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize, String keywords) {
        Map<String, Object> map = new HashMap<>();
        Result result = new Result();
        if (keywords!=null&&keywords.length()>0){
            Page<Article> page = articleService.selectPage(pageNum, pageSize, keywords);
            map.put("hasPrevious",page.hasPrevious());
            map.put("hasNext",page.hasNext());
            map.put("pageInfo",page);
            result.setObj(map);
            return result;
        }
        Page<Article> page = articleService.selectAll(pageNum, pageSize);
        map.put("hasPrevious",page.hasPrevious());
        map.put("hasNext",page.hasNext());
        map.put("pageInfo",page);
        result.setObj(map);
        return result;
    }

    @RequestMapping("getFavCount")
    @ResponseBody
    public Result getFavCount(Integer id){
        Result result = new Result();
        Integer count = articleService.getFavCount(id);
        result.setObj(count);
        return result;
    }
}
