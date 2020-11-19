package com.yjf.dao;

import com.yjf.entity.Article;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author 余俊锋
 * @date 2020/11/19 13:33
 * @Description
 */
@org.apache.ibatis.annotations.Mapper
public interface ArticleDao extends Mapper<Article> {
    Integer selectFavCount(Integer id);
}
