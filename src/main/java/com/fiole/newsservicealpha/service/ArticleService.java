package com.fiole.newsservicealpha.service;

import com.fiole.newsservicealpha.entity.Article;
import com.fiole.newsservicealpha.dao.ArticleRepository;
import com.fiole.newsservicealpha.entity.Comment;
import com.fiole.newsservicealpha.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.*;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    CommentService commentService;

    @Transactional
    public int doComment(int id, Comment comment){
        Article article = articleRepository.findArticlesByIdAndState(id,1);
        if (article == null){
            return 0;
        }
        int number = article.getResponseNumber() + 1;
        articleRepository.updateCommentsNumber(id,number,new Date());
        commentService.saveComment(comment);
        return number;
    }

    @Transactional
    public int updateBrowseNumber(int id){
        Article articlesByIdAndState = articleRepository.findArticlesByIdAndState(id, 1);
        int number = 0;
        if (articlesByIdAndState != null){
            number = articlesByIdAndState.getBrowseNumber() + 1;
        }
        articleRepository.updateBrowseNumber(id,number);
        return number;
    }

    public Article saveArticle(Article article){
        return articleRepository.save(article);
    }

    public Article getArticleById(int id){
        return articleRepository.findArticlesByIdAndState(id,1);
    }

    public Page<Article> getArticlesByTypeAndPaging(int page, int pageSize, int type,String sortedColumn){
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, sortedColumn);
        Specification<Article> specification = (Specification<Article>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("state").as(Integer.class),1));
            list.add(criteriaBuilder.equal(root.get("type").as(Integer.class),type));
            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        };
        return articleRepository.findAll(specification,pageable);
    }

    public Page<Article> getHotArticles(int start,int pageSize,int type){
        Pageable pageable = PageRequest.of(start, pageSize, Sort.Direction.DESC, "browseNumber");
        Specification<Article> specification = (Specification<Article>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.greaterThan(root.get("time").as(Date.class), TimeUtil.getTodayZeroOClock()));
            list.add(criteriaBuilder.equal(root.get("state").as(Integer.class),1));
            if (type != 0) {
                list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), type));
            }
            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        };
        return articleRepository.findAll(specification,pageable);
    }

    public Page<Article> getSearchResult(int start,int pageSize,String keyword){
        Pageable pageable = PageRequest.of(start, pageSize, Sort.Direction.DESC, "id");
        Specification<Article> specification = (Specification<Article>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.like(root.get("title").as(String.class),"%" + keyword + "%"));
            list.add(criteriaBuilder.equal(root.get("state").as(Integer.class),1));
            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        };
        return articleRepository.findAll(specification,pageable);
    }

    public long getArticleNumbers(){
        return articleRepository.countByState(1);
    }

    public long getArticleNumbersByType(int type){
        return articleRepository.countByTypeAndState(type,1);
    }
}
