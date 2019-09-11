package com.fiole.newsservicealpha.model;

import com.fiole.newsservicealpha.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentModel {
    private Comment comment;
    private long commentsNo; // 评论楼层
}
