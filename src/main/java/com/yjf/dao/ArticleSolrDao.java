package com.yjf.dao;

import com.yjf.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 余俊锋
 * @date 2020/11/19 9:34
 * @Description :继承接口，设置实例泛型和主键泛型
 */
@Repository
public interface ArticleSolrDao extends SolrCrudRepository<Article,String> {
    //命名法查询分页
    Page<Article> findByKeywordsOrderByBrowseCountDesc(String keywords, Pageable pageable);

    @Highlight(fields = {"title","content"},prefix = "<font color='red'>",postfix = "</font>")
    HighlightPage<Article> findByKeywords(String keywords, Pageable pageable);
}
