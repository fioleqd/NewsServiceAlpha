package com.fiole.newsservicealpha.model;

import com.fiole.newsservicealpha.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentModelDO {
    private List<CommentModel> commentModels;
    private int commentModelNumbers;
    private int commentNumbers;

    public void insertNewComment(Comment comment){
        if (commentModels == null || commentModels.isEmpty()){
            CommentModel commentModel = new CommentModel();
            commentModel.setComment(comment);
            commentModel.setCommentsNo(1);
            commentModels.add(commentModel);
        }
        else {
            CommentModel commentModel = new CommentModel();
            commentModel.setComment(comment);
            commentModel.setCommentsNo(commentModels.get(0).getCommentsNo() + 1);
            if (commentModels.size() >= 10){
                commentModels.remove(0);
            }
            commentModels.add(0,commentModel);
        }
    }
}
