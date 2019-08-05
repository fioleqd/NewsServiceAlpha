package com.fiole.newsservicealpha.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "tb_article")
public class Article implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String content;
    private String brief;
    private String source;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date time;
    private int responseNumber;
    private int browseNumber;
    private int type;
    private int state;
    private Date createTime;
    private Date updateTime;
}
