package com.fiole.newsservicealpha.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class SubmitCommentRequestModel {
    @NotEmpty
    private int itemId;
    @NotEmpty
    private int userId;
    @NotEmpty
    private String nickname;
    @NotEmpty
    private String content;
    @NotEmpty
    private int commentsNumber;
}
