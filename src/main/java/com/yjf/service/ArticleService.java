package com.yjf.service;

import com.yjf.entity.Article;
import org.springframework.data.domain.Page;

/**
 * @author 余俊锋
 * @date 2020/11/19 10:11
 * @Description
 */
public interface ArticleService extends BaseService<Article,String> {

    Page<Article> selectPage(Integer pageNum, Integer pageSize, String keyword);

    Page<Article> selectAll(Integer pageNum,Integer pageSize);

    Integer getFavCount(Integer id);

}