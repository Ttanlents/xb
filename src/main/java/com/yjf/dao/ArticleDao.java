package com.yjf.dao;

import com.yjf.entity.Article;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/11/19 13:33
 * @Description
 */
@org.apache.ibatis.annotations.Mapper
public interface ArticleDao extends Mapper<Article> {
    Integer selectFavCount(Integer id);

    @Delete("delete from favorite where u_id=#{uId} and a_id=#{aId}")
    void deleteFavorite(@Param("uId") Integer uId,@Param("aId") Integer aId);

    @Insert("insert into favorite VALUES(#{uId},#{aId})")
    void insertFavorite(@Param("uId")Integer uId,@Param("aId")Integer aId);

    @Select("select count(1) from favorite where u_id=#{uId} and  a_id=#{aId}")
    Integer getCollectionStatus(@Param("uId")Integer uId,@Param("aId")Integer aId);


    List<Article> selectCollectArticle(@Param("userId") Integer userId,@Param("title") String title);


    @Select("SELECT count(1) FROM `article` where publish_date BETWEEN DATE_SUB(NOW(),interval 8 day) and now()")
    int selectRecentArticleCount();

    @Select("SELECT count(1) count,date_format(publish_date, '%Y-%m-%d') register_time FROM `article`  where DATEDIFF(now(),publish_date)<9  group by date_format(publish_date, '%m-%d')")
    List<Map<String,String>> selectCurrentDayCount();

    @Update("update article set article.browse_count=article.browse_count+#{count} where article.id=#{id}")
    void updateArticleBrowseCount(@Param("id") Integer id,@Param("count") Integer count);
}
