package com.fiole.newsservicealpha.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentModelDO {
    private List<CommentModel> commentModels;
    private int commentModelNumbers;
    private int commentNumbers;
}
