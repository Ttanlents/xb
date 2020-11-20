package com.yjf.controller;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.Article;
import com.yjf.entity.Result;
import com.yjf.entity.User;
import com.yjf.service.ArticleService;
import com.yjf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    @Autowired
    UserService userService;
    /**
     *@Description TODO:文章列表  索引库分页查询
     *@author 余俊锋
     *@date 2020/11/19 17:08
     *@params pageNum
     * @param pageSize
     * @param keywords
     *@return com.yjf.entity.Result
     */
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
    /**
     *@Description TODO:通过 id 获取文章收藏次数
     *@author 余俊锋
     *@date \ 17:07
     *@params id
     *@return com.yjf.entity.Result
     */
    @RequestMapping("getFavCount")
    @ResponseBody
    public Result getFavCount(Integer id){
        Result result = new Result();
        Integer count = articleService.getFavCount(id);
        result.setObj(count);
        return result;
    }

    /**
     *@Description TODO: 查询登陆用户关注的人  中收藏了此文章的人
     *@author 余俊锋
     *@date 2020/11/19 17:10
     *@params id
     *@return com.yjf.entity.Result
     */
    @RequestMapping("getUsersByArticleId")
    @ResponseBody
    public Result getUsersByArticleId(Integer userId,Integer articleId){
        Result result = new Result();
        List<User> userList = userService.selectUsersByArticleId(userId,articleId);
        result.setObj(userList);
        return result;
    }
    /**
     *@Description TODO:查询是否已经收藏
     *@author 余俊锋
     *@date 2020/11/19 18:00
     *@params userId
     * @param articleId
     *@return com.yjf.entity.Result
     */
    @RequestMapping("getCollectionStatus")
    @ResponseBody
    public Result getCollectionStatus(Integer userId,Integer articleId){
        Result result = new Result();
        Boolean status = articleService.getCollectionStatus(userId, articleId);
        result.setObj(status);
        return result;
    }
    /**
     *@Description TODO:该表登陆用户收藏的文章状态
     *@author 余俊锋
     *@date 2020/11/19 18:12
     *@params userId
     * @param articleId
     *@return com.yjf.entity.Result
     */
    @RequestMapping("changeCollect")
    @ResponseBody
    public Result changeCollect(Integer userId,Integer articleId,Boolean status){
        Result result = new Result();
        articleService.changeCollection(status,userId,articleId);
        if (status){
            result.setMsg("已取消收藏");
            result.setObj(false);
            return  result;
        }
        result.setObj(true);
        result.setMsg("收藏成功");
        return result;
    }

    /**
     *@Description TODO；发布的数据保存到数据库  和 索引库
     *@author 余俊锋
     *@date 2020/11/19 19:48
     *@params article
     * @param session
     *@return com.yjf.entity.Result
     */
    @RequestMapping(value = "doSaveArticle",method = RequestMethod.PUT)
    @ResponseBody
    public Result doSaveArticle(@RequestBody Article article, HttpSession session){
        Result result = new Result();
        User loginUser =(User) session.getAttribute("loginUser");
        if (loginUser!=null){
            article.setUserId(loginUser.getId());
            article.setPublishRealName(loginUser.getRealName());
        }
        article.setPublishDate(new Date());
        int i = articleService.insertSelective(article);
        if (i>0){
            result.setMsg("发布成功");
            Article article1 = articleService.saveArticleForSolr(article);//保存到索引库
            return result;
        }
        result.setMsg("发布失败");
        result.setSuccess(false);
        return result;
    }


    @RequestMapping(value = "selectCollectPage/{pageNum}/{pageSize}")
    @ResponseBody
    public Result selectCollectPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize,Integer id,String title){
        PageInfo<Article> pageInfo = articleService.selectCollectArticle(pageNum, pageSize, id,title);
        Result result = new Result();
        result.setObj(pageInfo);
        return result;
    }
}
