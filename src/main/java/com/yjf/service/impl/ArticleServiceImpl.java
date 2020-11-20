package com.yjf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjf.dao.ArticleDao;
import com.yjf.dao.ArticleSolrDao;
import com.yjf.entity.Article;
import com.yjf.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/11/19 10:12
 * @Description
 */
@Service
public class ArticleServiceImpl extends BaseServiceImpl<Article,String> implements ArticleService {
    @Autowired
    ArticleSolrDao articleSolrDao;
    @Autowired
    ArticleDao articleDao;


    @Override
    public Page<Article> selectPage(Integer pageNum, Integer pageSize, String keyword) {
        //Sort sort=Sort.by(Sort.Direction.DESC,"browseCount");
        //PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, sort);
        Page<Article> page = articleSolrDao.findByKeywordsOrderByBrowseCountDesc(keyword, PageRequest.of(pageNum-1,pageSize));
        return page;
    }

    @Override
    public Page<Article> selectAll(Integer pageNum, Integer pageSize) {
        //根据浏览量排序
        Sort sort=Sort.by(Sort.Direction.DESC,"browseCount");
        Page<Article> page = articleSolrDao.findAll(PageRequest.of(pageNum - 1, pageSize, sort));
        return page;
    }

    @Override
    public Integer getFavCount(Integer id) {
       return articleDao.selectFavCount(id);
    }

    @Override
    public void changeCollection(Boolean flag,Integer uId,Integer aId) {
        if (flag){ //已经收藏了，需要取消收藏
            articleDao.deleteFavorite(uId,aId);
        }else {
            articleDao.insertFavorite(uId,aId);
        }
    }

    @Override
    public Boolean getCollectionStatus(Integer uId, Integer aId) {
        Integer status = articleDao.getCollectionStatus(uId, aId);
        if (status==1){
            return true;
        }
        return false;
    }

    @Override
    public Article saveArticleForSolr(Article article) {
        return articleSolrDao.save(article);
    }

    @Override
    public PageInfo<Article> selectCollectArticle(Integer pageNum,Integer pageSize,Integer userId,String title) {
        PageHelper.startPage(pageNum,pageSize);
        List<Article> articles = articleDao.selectCollectArticle(userId,title);
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        return pageInfo;
    }


}
