package com.fiole.newsservicealpha.service;

import com.fiole.newsservicealpha.entity.Comment;
import com.fiole.newsservicealpha.dao.CommentRepository;
import com.fiole.newsservicealpha.model.CommentModel;
import com.fiole.newsservicealpha.model.CommentModelDO;
import com.fiole.newsservicealpha.util.Page2ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public int getCommentNumbers(int itemId){
        return commentRepository.countByItemIdAndState(itemId,1);
    }

    public Comment saveComment(Comment comment){
        return commentRepository.save(comment);
    }

    public CommentModelDO getCommentsByPaging(int page, int pageSize, int itemId, Comment comment){
        int commentNumbers = getCommentNumbers(itemId);
        return getCommentsByPaging(page,pageSize,itemId,commentNumbers + 1,comment);
    }

    public CommentModelDO getCommentsByPaging(int page, int pageSize, int itemId, int commentNumbers, Comment comment){
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        Specification<Comment> specification = (Specification<Comment>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("itemId").as(Integer.class), itemId));
            list.add(criteriaBuilder.equal(root.get("state").as(Integer.class),1));
            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        };
        Page<Comment> all = commentRepository.findAll(specification,pageable);
        List<CommentModel> comments = new ArrayList<>();
        if (comment != null){
            CommentModel commentModel = new CommentModel(comment,commentNumbers);
            comments.add(commentModel);
        }
        int offset = comments.size();
        List<Comment> page2List = Page2ListUtil.page2List(all);
        for (int i = 0;i < page2List.size();i++){
            CommentModel commentModel = new CommentModel(page2List.get(i),commentNumbers - i - offset);
            comments.add(commentModel);
        }
        return new CommentModelDO(comments,commentNumbers);
    }
}
