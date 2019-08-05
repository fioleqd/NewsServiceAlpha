package com.fiole.newsservicealpha.dao;


import com.fiole.newsservicealpha.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Integer>, JpaSpecificationExecutor<Article> {
    long countByTypeAndState(int type,int state);
    long countByState(int state);
    Article findArticlesByIdAndState(int id,int state);
    @Modifying
    @Query("update Article a set a.browseNumber = ?2 where a.id = ?1")
    void updateBrowseNumber(int id,int browseNumber);
    @Modifying
    @Query("update Article a set a.responseNumber = ?2, a.updateTime = ?3 where a.id = ?1")
    void updateCommentsNumber(int id, int responseNumber,Date updateTime);
}
