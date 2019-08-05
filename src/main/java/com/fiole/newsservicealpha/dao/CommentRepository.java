package com.fiole.newsservicealpha.dao;

import com.fiole.newsservicealpha.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer>,JpaSpecificationExecutor<Comment> {
    long countByItemIdAndState(int itemId,int state);

}
