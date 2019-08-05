package com.fiole.newsservicealpha.service;

import com.fiole.newsservicealpha.entity.Comment;
import com.fiole.newsservicealpha.dao.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public long getCommentNumbers(int itemId){
        return commentRepository.countByItemIdAndState(itemId,1);
    }

    public Comment saveComment(Comment comment){
        return commentRepository.save(comment);
    }

    public Page<Comment> getCommentsByPaging(int page, int pageSize, int itemId){
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        Specification<Comment> specification = (Specification<Comment>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("itemId").as(Integer.class), itemId));
            list.add(criteriaBuilder.equal(root.get("state").as(Integer.class),1));
            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        };
        Page<Comment> all = commentRepository.findAll(specification,pageable);
        return all;
    }
}
