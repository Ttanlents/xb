package com.yjf.service.impl;

import com.yjf.dao.ArticleDao;
import com.yjf.dao.ArticleJpaDao;
import com.yjf.entity.Article;
import com.yjf.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author 余俊锋
 * @date 2020/11/19 10:12
 * @Description
 */
@Service
public class ArticleServiceImpl extends BaseServiceImpl<Article,String> implements ArticleService {
    @Autowired
    ArticleJpaDao articleJpaDao;
    @Autowired
    ArticleDao articleDao;


    @Override
    public Page<Article> selectPage(Integer pageNum, Integer pageSize, String keyword) {
        //Sort sort=Sort.by(Sort.Direction.DESC,"browseCount");
        //PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, sort);
        Page<Article> page = articleJpaDao.findByKeywordsOrderByBrowseCountDesc(keyword, PageRequest.of(pageNum-1,pageSize));
        return page;
    }

    @Override
    public Page<Article> selectAll(Integer pageNum, Integer pageSize) {
        //根据浏览量排序
        Sort sort=Sort.by(Sort.Direction.DESC,"browseCount");
        Page<Article> page = articleJpaDao.findAll(PageRequest.of(pageNum - 1, pageSize, sort));
        return page;
    }

    @Override
    public Integer getFavCount(Integer id) {
       return articleDao.selectFavCount(id);
    }
}
