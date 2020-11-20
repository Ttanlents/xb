package com.yjf.dao;

import com.yjf.entity.Article;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

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
}
