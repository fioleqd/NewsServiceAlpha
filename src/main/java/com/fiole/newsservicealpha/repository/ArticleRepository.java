package com.fiole.newsservicealpha.repository;


import com.fiole.newsservicealpha.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Integer> {
    List<Article> findArticlesByType(int type);
}
