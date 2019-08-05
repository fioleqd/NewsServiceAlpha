package com.fiole.newsservicealpha.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "tb_response")
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int itemId;
    private int userId;
    private String nickname;
    private String content;
    private int parentId;
    private String path;
    private int state;
    private Date createTime;
    private Date updateTime;
}
